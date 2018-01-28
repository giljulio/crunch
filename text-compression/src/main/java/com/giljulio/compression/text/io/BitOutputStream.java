package com.giljulio.compression.text.io;

public interface BitOutputStream {

    void writeBit(boolean bit);

    void close();
}
