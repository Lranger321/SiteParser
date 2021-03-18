package persistance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logs {

    private static Logger logger;

    private static boolean exist = false;

    public static void init() {
        if (!exist) {
            logger = LogManager.getRootLogger();
            exist = true;
        }
    }

    public static void error(Exception e) {
        logger.error(e);
    }

    public static void info(String info) {
        logger.info(info);
    }

}
