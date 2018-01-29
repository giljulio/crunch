package com.giljulio.crunch.text.io;

import java.io.*;

public class FileBitOutputStream implements BitOutputStream {

    private final BufferedOutputStream out;

    private int buffer;
    private int n;

    public FileBitOutputStream(OutputStream os) {
        out = new BufferedOutputStream(os);
    }

    @Override
    public void writeBit(boolean bit) {
        buffer <<= 1;
        if (bit) {
            buffer |= 1;
        }

        n++;
        if (n == 8) {
            clearBuffer();
        }
    }

    @Override
    public void close() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearBuffer() {
        if (n == 0) {
            return;
        }
        if (n > 0) {
            buffer <<= (8 - n);
        }
        try {
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffer = 0;
    }
}