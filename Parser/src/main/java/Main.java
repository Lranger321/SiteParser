import persistance.*;

import java.sql.SQLException;


public class Main {

    private static final String URL = "http://az.lib.ru/t/tolstoj_lew_nikolaewich/text_0073.shtml";

    public static void main(String[] args) {
        long seconds = System.currentTimeMillis();
        Logs.init();
        DataBase dataBase = getDataBase();
        Writer writer = new Writer(dataBase);
        writer.writeToFile(URL);
        Parser parser = new Parser(dataBase, writer);
        parser.parseFile();
        System.out.println("Everything is ok, time:"+(System.currentTimeMillis()-seconds)/1000+" s");
    }

    private static DataBase getDataBase() {
        DataBase dataBase;
        try {
            dataBase = new MySQLDataBase();
            return dataBase;
        } catch (SQLException ex) {
            Logs.info("Couldn't create MysqlDatabase");
            Logs.error(ex);
        }
        dataBase = new HashMapDataBase();
        return dataBase;
    }

}
