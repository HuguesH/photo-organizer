package hh.tools;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 */
public class App {
    private static Logger LOG = LoggerFactory.getLogger(App.class);

    //private static final Path PHO_USER_DIR = Paths.get(System.getProperty("user.home"), "Pictures", "Photos");
    private static final Path PHO_USER_DIR = Paths.get("D:/win-users/Hugues/Pictures/Photos");

    public static void main(String[] args) {
        WriteAppName();
        Options cmdOptions = getCliOptions();
        // create the parser
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(cmdOptions, args);
            if (line.hasOption("d")) setLogLevel("DEBUG", "hh.tools");
            LOG.debug("default directory work on {}",PHO_USER_DIR.toFile().getAbsolutePath());
            String subDirectory = line.getOptionValue("y");
            Path directory = PHO_USER_DIR.resolve(subDirectory);
            LOG.debug("sub directory passe {}",directory.toFile().getAbsolutePath());
            PhotoAnalyser.build(directory).searchImagesCopy();


        } catch (Exception exp) {
            // oops, something went wrong
            LOG.error("Parsing failed.  Reason: ", exp);
        }

    }

    private static Options getCliOptions() {
        Options cmdOptions = new Options();
        cmdOptions.addOption(Option.builder("h").longOpt("help").desc("Affiche le message d'aide").build());
        cmdOptions.addOption(new Option("d", "debug", false, "Turn on debug."));
        cmdOptions.addOption(new Option("e", "extract", false, "Turn on extract."));
        cmdOptions.addOption(Option.builder("y").longOpt("year").hasArg().desc("subdirectory under default path"+PHO_USER_DIR.toFile().getAbsolutePath()).build());
        return cmdOptions;
    }

    private static void WriteAppName() {
        LOG.info(" _____  _    _  ____          ____  _____   _____ ");
        LOG.info("|  __ \\| |  | |/ __ \\        / __ \\|  __ \\ / ____|");
        LOG.info("| |__) | |__| | |  | |______| |  | | |__) | |  __ ");
        LOG.info("|  ___/|  __  | |  | |______| |  | |  _  /| | |_ |");
        LOG.info("| |    | |  | | |__| |      | |__| | | \\ \\| |__| |");
        LOG.info("|_|    |_|  |_|\\____/        \\____/|_|  \\_\\\\_____|");
    }

    private static void setLogLevel(String logLevel, String packageName) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(packageName);
        LOG.info("{} current logger level: {}", packageName, logger.getLevel());
        LOG.info(" You entered: {}", logLevel);

        logger.setLevel(Level.toLevel(logLevel));
    }
}
