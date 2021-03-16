import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    private static final String URL = "https://www.simbirsoft.com/";

    public static Logger logger;

    public static void main(String[] args) throws IOException, SQLException {
        logger = LogManager.getRootLogger();
        DataBase dataBase = new DataBase();
        Writer writer = new Writer(dataBase);
        writer.writeToFile(URL);
        Parser parser = new Parser(dataBase,writer);
        parser.parseFile();
        System.out.println("Everything is ok");
    }

}
