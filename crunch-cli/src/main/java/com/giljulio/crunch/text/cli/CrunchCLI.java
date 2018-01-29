package com.giljulio.crunch.text.cli;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.CompressorReader;
import com.giljulio.crunch.text.compressor.reader.PlainTextFileReader;
import com.giljulio.crunch.text.compressor.reader.StringReader;
import com.giljulio.crunch.text.compressor.writer.BinaryFileWriter;
import com.giljulio.crunch.text.compressor.writer.CompressorWriter;
import com.giljulio.crunch.text.compressor.writer.StringWriter;
import com.giljulio.crunch.text.decompressor.reader.BinaryFileDecompressReader;
import com.giljulio.crunch.text.decompressor.reader.DecompressReader;
import com.giljulio.crunch.text.decompressor.writer.DecompressWriter;
import com.giljulio.crunch.text.decompressor.writer.PlainTextFileDecompressWriter;
import com.giljulio.crunch.text.decompressor.writer.StringDecompressWriter;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Example usages:
 *
 * {@code crunch --help }
 *
 * {@code crunch --compress "Ancient Egyptian literature was written in the Egyptian language." }
 * {@code crunch --compress --sourceFile ancient_egyptian_literature.txt }
 * {@code crunch --compress --sourceFile ancient_egyptian_literature.txt --destinationFile compressed.bin }
 *
 * {@code crunch --decompress --sourceFile compressed.bin }
 * {@code crunch --decompress --sourceFile compressed.bin --destinationFile ancient_egyptian_literature.txt }
 */
public class CrunchCLI {

    private static final CommandLineParser PARSER = new DefaultParser();
    private static final Options OPTIONS = new Options();

    private static final String OPT_COMPRESS = "compress";
    private static final String OPT_DECOMPRESS = "decompress";
    private static final String OPT_SOURCE_FILE = "sourceFile";
    private static final String OPT_DESTINATION_FILE = "destinationFile";
    private static final String OPT_HELP = "help";

    static {
        OPTIONS.addOption(Option.builder()
                .longOpt(OPT_COMPRESS)
                .desc("Compress source, either from a file or text")
                .optionalArg(true)
                .argName("text")
                .hasArg()
                .build());

        OPTIONS.addOption(Option.builder()
                .longOpt(OPT_DECOMPRESS)
                .desc("Decompress source from a file")
                .build());

        OPTIONS.addOption(Option.builder()
                .longOpt(OPT_SOURCE_FILE)
                .argName("sourceFile")
                .hasArg()
                .desc("File directory")
                .build());

        OPTIONS.addOption(Option.builder()
                .longOpt(OPT_DESTINATION_FILE)
                .argName("destination")
                .hasArg()
                .desc("File directory")
                .build());

        OPTIONS.addOption(Option.builder()
                .longOpt(OPT_HELP)
                .desc("Help")
                .build());
    }

    public static void main(String[] args) {
        new CrunchCLI().parse(args);
    }

    private void parse(String[] args) {
        try {
            CommandLine cmd = PARSER.parse(OPTIONS, args);

            if (cmd.hasOption(OPT_COMPRESS) && cmd.hasOption(OPT_DECOMPRESS)) {
                throw new IllegalArgumentException("Arguments: " + OPT_COMPRESS + " "
                        + OPT_DECOMPRESS + " cannot be used in conjunction.");
            }
            if (cmd.hasOption(OPT_HELP)) {
                new HelpFormatter().printHelp("crunch", OPTIONS);
                return;
            }

            Crunch crunch = new Crunch.Builder().build();
            System.out.println(execute(cmd, crunch));

        } catch (ParseException | FileNotFoundException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Object execute(CommandLine cmd, Crunch crunch) throws FileNotFoundException {
        if (cmd.hasOption(OPT_COMPRESS)) {
            return crunch.compress(compressReader(cmd), compressWriter(cmd));
        } else if (cmd.hasOption(OPT_DECOMPRESS)) {
            return crunch.decompress(decompressReader(cmd), decompressWriter(cmd));
        }

        return null;
    }

    private DecompressReader decompressReader(CommandLine cmd) throws FileNotFoundException {
        if (cmd.hasOption(OPT_SOURCE_FILE)) {
            String path = cmd.getOptionValue(OPT_SOURCE_FILE);
            if (path != null) {
                return new BinaryFileDecompressReader(new File(path));
            }
        }

        throw new IllegalArgumentException(OPT_SOURCE_FILE + " missing path. ");
    }

    private DecompressWriter decompressWriter(CommandLine cmd) throws FileNotFoundException {
        if (cmd.hasOption(OPT_DESTINATION_FILE)) {
            String path = cmd.getOptionValue(OPT_DESTINATION_FILE);
            if (path != null) {
                return new PlainTextFileDecompressWriter(new File(path));
            }
        }

        return new StringDecompressWriter();
    }

    private CompressorWriter compressWriter(CommandLine cmd) throws FileNotFoundException {
        if (cmd.hasOption(OPT_DESTINATION_FILE)) {
            String path = cmd.getOptionValue(OPT_DESTINATION_FILE);
            return new BinaryFileWriter(new File(path));
        }

        return new StringWriter();
    }

    private CompressorReader compressReader(CommandLine cmd) throws FileNotFoundException {
        String input = cmd.getOptionValue(OPT_COMPRESS);
        if (input != null) {
            return new StringReader(input);
        } else if (cmd.hasOption(OPT_SOURCE_FILE)) {
            String path = cmd.getOptionValue(OPT_SOURCE_FILE);
            return new PlainTextFileReader(new File(path));
        }

        throw new IllegalArgumentException(OPT_COMPRESS + " must have a source");
    }
}
