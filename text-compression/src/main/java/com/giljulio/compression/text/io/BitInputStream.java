package com.giljulio.compression.text.io;

public interface BitInputStream {

    boolean hasNext();

    boolean readBit();

    void close();
}
