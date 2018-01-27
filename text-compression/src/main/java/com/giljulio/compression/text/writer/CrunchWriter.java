package com.giljulio.compression.text.writer;

public interface CrunchWriter<T> {

    void write(char character);

    T output();
}
