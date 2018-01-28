package com.giljulio.compression.text.util;

public interface BitOutputStream {
    void writeBit(boolean x);

    void flush();

    void close();
}
