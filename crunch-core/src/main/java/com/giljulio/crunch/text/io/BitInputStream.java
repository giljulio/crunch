package com.giljulio.crunch.text.io;

public interface BitInputStream {

    boolean hasNext();

    boolean readBit();

    void close();
}
