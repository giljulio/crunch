package com.giljulio.compression.text.io;

import java.io.*;

public class FileBitOutputStream implements BitOutputStream {

    private int buffer;
    private int n;
    private BufferedOutputStream out;

    public FileBitOutputStream(OutputStream os) {
        out = new BufferedOutputStream(os);
    }

    @Override
    public void writeBit(boolean x) {
        buffer <<= 1;
        if (x) {
            buffer |= 1;
        }

        n++;
        if (n == 8) {
            clearBuffer();
        }
    }

    @Override
    public void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        flush();
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