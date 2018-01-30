package com.giljulio.crunch.text;

import com.giljulio.crunch.text.util.CircularQueue;
import org.junit.Before;
import org.junit.Test;

import java.nio.BufferOverflowException;

import static org.junit.Assert.assertEquals;

public class CircularQueueTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void given_queueCirclesAroundArray_then_checkContents() throws Exception {
        CircularQueue<String> queue = new CircularQueue<>(3);

        queue.add("a");
        queue.add("b");
        queue.removeLast();
        queue.removeLast();
        queue.add("c");
        queue.add("d");

        assertEquals("c", queue.get(0));
        assertEquals("d", queue.get(1));
    }

    @Test
    public void given_itemsAdded_when_queueIsEmpty_then_checkSize() throws Exception {
        CircularQueue<String> queue = new CircularQueue<>(3);

        queue.add("a");
        queue.add("b");
        queue.removeLast();
        queue.removeLast();
        queue.add("c");
        queue.add("d");

        assertEquals(2, queue.size());
    }

    @Test
    public void given_itemsAdded_when_queueLoops_then_checkSize() throws Exception {
        CircularQueue<String> queue = new CircularQueue<>(3);

        queue.add("a");
        queue.add("b");

        assertEquals(2, queue.size());
    }

    @Test
    public void given_itemsAdded_when_empty_then_checkSize() throws Exception {
        CircularQueue<String> queue = new CircularQueue<>(3);

        queue.add("a");
        queue.removeLast();
        queue.removeLast();
        queue.add("b");

        assertEquals(0, queue.size());
    }

    @Test(expected = BufferOverflowException.class)
    public void given_itemsAdded_when_reachesCapacity_then_throwExeception() throws Exception {
        CircularQueue<String> queue = new CircularQueue<>(2);

        queue.add("a");
        queue.add("b");
        queue.add("c");
    }
}