package utils;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
    static Logger logger = Logger.getLogger(Log.class);

    public static void startTestCase(String testCaseName) {
    	URL logConfig = Log.class.getClassLoader().getResource("log4j.properties");      
    	PropertyConfigurator.configure(logConfig);
        logger.info("\n==================== " + testCaseName + " ====================");
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void endTestCase() {
        logger.info("==================== END ====================\n");
    }
}
