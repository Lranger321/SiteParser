import persistance.*;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {

    private static String url;

    public static void main(String[] args) {
        Logs.init();
        if(args.length > 0) {
            url = args[0];
        }else{
            System.out.println("Insert url");
            Scanner scanner = new Scanner(System.in);
            url = scanner.next();
        }
        long seconds = System.currentTimeMillis();
        DataBase dataBase = getDataBase();
        Writer writer = new Writer(dataBase);
        writer.writeToFile(url);
        Parser parser = new Parser(dataBase, url, writer);
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
