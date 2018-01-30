package com.giljulio.crunch.text;

import java.nio.BufferOverflowException;

public class CircularQueue<T> {

    private final Object[] elements;
    private int head, tail;

    public CircularQueue(int capacity) {
        elements = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
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
