import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private final String DBNAME = "notes";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final Connection connection;
    private StringBuilder multiInsert;

    public DataBase() throws SQLException {
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
            try {
                executeInsert();
            } catch (SQLException ex) {
                Main.logger.info("INSERT INTO word_count(name,count) " +
                        "VALUES " + multiInsert.toString() +
                        " ON DUPLICATE KEY UPDATE count=count + 1");
                Main.logger.error(ex);
                multiInsert = new StringBuilder();
                ex.printStackTrace();
            }
            multiInsert = new StringBuilder();
        }
    }

    public void executeInsert() throws SQLException {
        String query = "INSERT INTO word_count(name,count) " +
                "VALUES " + multiInsert.toString() +
                " ON DUPLICATE KEY UPDATE count=count + 1";
        connection.createStatement().execute(query);
    }

    public List<String> getStatistic() throws SQLException {
        List<String> statistic = new ArrayList<>();
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
        return statistic;
    }


}
