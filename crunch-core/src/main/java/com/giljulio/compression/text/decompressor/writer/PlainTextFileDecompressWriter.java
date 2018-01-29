package com.giljulio.compression.text.decompressor.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlainTextFileDecompressWriter implements DecompressWriter<File> {

    private final File file;
    private final BufferedWriter out;

    public PlainTextFileDecompressWriter(File file) {
        this.file = file;
        try {
            out = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(char character) {
        try {
            out.write(character);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File output() {
        try {
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.close();
        } catch (IOException e) {
            // ignored
        }
        return file;
    }
}
