package com.giljulio.compression.text.writer;

public interface CrunchWriter<T> {

    void writeCharacter(char character);

    void writeReference(int relativeOffset, int length);

    T output();
}
