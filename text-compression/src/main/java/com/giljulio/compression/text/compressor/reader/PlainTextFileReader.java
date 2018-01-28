package com.giljulio.compression.text.compressor.reader;

import java.io.*;

/**
 * PlainTextFileReader is a {@link File } reader than reads data on-the-fly.
 */
public class PlainTextFileReader implements CompressorReader {

    private final BufferedReader reader;

    public PlainTextFileReader(File file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public char read() {
        try {
            int character = reader.read();
            if (character != -1) {
                return ((char) character);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return '\u0000';
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            // ignored
        }
    }
}
