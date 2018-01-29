package com.giljulio.crunch.text.io;

import java.io.*;
import java.util.NoSuchElementException;

public class FileBitInputStream implements BitInputStream {

    private static final int END_OF_FILE = -1;

    private final BufferedInputStream in;

    private int buffer;
    private int n;

    public FileBitInputStream(File file) throws FileNotFoundException {
        in = new BufferedInputStream(new FileInputStream(file));
        fillBuffer();
    }

    @Override
    public boolean hasNext() {
        return buffer != END_OF_FILE;
    }

    @Override
    public boolean readBit() {
        if (buffer == END_OF_FILE) {
            throw new NoSuchElementException("Empty InputStream");
        }
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) {
            fillBuffer();
        }
        return bit;
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            // ignored
        }
    }

    private void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            buffer = END_OF_FILE;
            n = -1;
        }
    }
}
