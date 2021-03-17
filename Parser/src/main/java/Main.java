import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    private static final String URL = "http://az.lib.ru/t/tolstoj_lew_nikolaewich/text_0073.shtml";

    public static Logger logger;

    public static void main(String[] args){
        logger = LogManager.getRootLogger();
        DataBase dataBase = null;
        try {
            dataBase = new DataBase();
        } catch (SQLException ex) {
            Main.logger.error(ex);
            ex.printStackTrace();
        }
        Writer writer = new Writer(dataBase);
        writer.writeToFile(URL);
        Parser parser = new Parser(dataBase,writer);
        parser.parseFile();
        System.out.println("Everything is ok");
    }

}
