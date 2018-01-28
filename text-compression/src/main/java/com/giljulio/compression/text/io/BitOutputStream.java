package com.giljulio.compression.text.io;

public interface BitOutputStream {
    void writeBit(boolean x);

    void flush();

    void close();
}
