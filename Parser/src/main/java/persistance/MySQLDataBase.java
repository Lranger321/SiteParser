package persistance;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySQLDataBase implements DataBase {

    private String DBNAME;
    private String USER;
    private String PASSWORD;
    private final Connection connection;
    private StringBuilder multiInsert;

    public MySQLDataBase() throws SQLException {
        loadProperties();
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/" + DBNAME +
                        "?&useUnicode=true&serverTimezone=UTC&&characterEncoding=UTF-8&" +
                        "user=" + USER + "&password=" + PASSWORD);
        connection.createStatement().execute("DROP TABLE IF EXISTS word_count");
        connection.createStatement().execute("CREATE TABLE word_count(" +
                "name VARCHAR (255) NOT NULL, " +
                "count INT NOT NULL, " +
                "PRIMARY KEY(name))");
        multiInsert = new StringBuilder();
    }

    public void concatMultiInsert(String word) {
        boolean isStart = multiInsert.length() == 0;
        multiInsert.append((isStart) ? "" : ",").append("('").append(word).append("', 1)");
        if (multiInsert.length() > 300000) {
            executeMultiInsert();
            multiInsert = new StringBuilder();
        }
    }

    public void executeMultiInsert() {
        String query = "INSERT INTO word_count(name,count) " +
                "VALUES " + multiInsert.toString() +
                " ON DUPLICATE KEY UPDATE count=count + 1";
        try {
            connection.createStatement().execute(query);
        } catch (SQLException ex) {
            Logs.info("INSERT INTO word_count(name,count) " +
                    "VALUES " + multiInsert.toString() +
                    " ON DUPLICATE KEY UPDATE count=count + 1");
            Logs.error(ex);
            multiInsert = new StringBuilder();
            ex.printStackTrace();
        }
    }

    public List<String> getStatistics() {
        List<String> statistic = new ArrayList<>();
        try {
            String sql = "SELECT name, count FROM word_count";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                StringBuilder stat = new StringBuilder();
                statistic.add(stat
                        .append(rs.getString("name"))
                        .append("-")
                        .append(rs.getString("count"))
                        .toString());
            }
        } catch (SQLException ex) {
            Logs.info("INSERT INTO word_count(name,count) " +
                    "VALUES " + multiInsert.toString() +
                    " ON DUPLICATE KEY UPDATE count=count + 1");
            Logs.error(ex);
            multiInsert = new StringBuilder();
            ex.printStackTrace();
        }


        return statistic;
    }


    private void loadProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("MySQL.properties"));
            USER = properties.getProperty("USER");
            DBNAME = properties.getProperty("DBNAME");
            PASSWORD = properties.getProperty("PASSWORD");
        } catch (IOException ex) {
            Logs.error(ex);
            Logs.info("Failed to attach file");
            ex.printStackTrace();
        }
    }


}
