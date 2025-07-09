package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.net.URL;

public class Log {

    private static boolean isConfigured = false;

    private static void configureLogger() {
        if (!isConfigured) {
            URL logConfig = Log.class.getClassLoader().getResource("log4j.properties");
            if (logConfig != null) {
                PropertyConfigurator.configure(logConfig);
                isConfigured = true;
            } else {
                System.err.println("❌ log4j.properties not found in resources!");
            }
        }
    }

    private static Logger getLogger(Class<?> clazz) {
        configureLogger();
        return Logger.getLogger(clazz);
    }

    public static void startTestCase(String testCaseName) {
        Logger logger = getLogger(Log.class);
        logger.info("\n════════════════════════════════════════════════════════════");
        logger.info("🔷 STARTING TEST: " + testCaseName);
        logger.info("════════════════════════════════════════════════════════════");
    }

    public static void endTestCase() {
        Logger logger = getLogger(Log.class);
        logger.info("🔶 END OF TEST");
        logger.info("════════════════════════════════════════════════════════════\n");
    }

    public static void info(String message) {
        getLogger(Log.class).info(message);
    }

    public static void warn(String message) {
        getLogger(Log.class).warn(message);
    }

    public static void error(String message) {
        getLogger(Log.class).error("❌ " + message);
    }

    public static void debug(String message) {
        getLogger(Log.class).debug("🐞 " + message);
    }
}
