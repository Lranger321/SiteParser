import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistance.*;

import java.sql.SQLException;


public class Main {

    private static final String URL = "http://az.lib.ru/t/tolstoj_lew_nikolaewich/text_0073.shtml";

    public static void main(String[] args){
        Logs.init();
        DataBase dataBase = getDataBase();
            Writer writer = new Writer(dataBase);
            writer.writeToFile(URL);
            Parser parser = new Parser(dataBase,writer);
            parser.parseFile();
            System.out.println("Everything is ok");
    }


    private static DataBase getDataBase(){
        DataBase dataBase;
        try {
            dataBase = new MySQLDataBase();
            return dataBase;
        } catch (SQLException ex) {
            dataBase = null;
            Logs.info("Couldn't create MysqlDatabase");
            Logs.error(ex);
        }
        try {
            dataBase = new MongoDB();
            return dataBase;
        } catch (Exception ex) {
            Logs.info("Couldn't create MongoDatabase");
            Logs.error(ex);
        }
        dataBase = new HashMapDataBase();
        return dataBase;
    }


}
