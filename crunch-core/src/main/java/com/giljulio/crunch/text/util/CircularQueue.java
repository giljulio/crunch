package com.giljulio.crunch.text.util;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class CircularQueue<T> {

    private final Object[] elements;
    private int head, tail;

    public CircularQueue(int capacity) {
        elements = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int i = absoluteIndex(index);
        return (T) elements[i];
    }

    public boolean add(T t) {
        if (size() >= elements.length) {
            throw new BufferOverflowException();
        }
        elements[head++ % elements.length] = t;
        return true;
    }

    public void removeLast() {
        if (size() == 0) {
            throw new BufferUnderflowException();
        }
        elements[tail++ % elements.length] = null;
    }

    public int size() {
        if (head > tail) {
            return head - tail;
        } else if (head < tail) {
            return head + elements.length - tail;
        }
        return 0;
    }

    private int absoluteIndex(int index) {
        return (tail + index) % elements.length;
    }
}
