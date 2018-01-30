package com.giljulio.crunch.text.compressor.writer;

import com.giljulio.crunch.text.io.BitOutputStream;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BinaryFileWriterTest {

    private BinaryFileWriter underTest;
    private MockBitOutputStream bitOutputStream;

    @Before
    public void setup() {
        bitOutputStream = new MockBitOutputStream();
        underTest = new BinaryFileWriter(bitOutputStream);
    }

    @Test
    public void when_writeCharacter_then_validateOutput() {
        char[] characters = new char[]{'a', 'b', 'c'};
        for (char character : characters) {
            underTest.writeCharacter(character);
        }

        int position = 0;
        int charPosition = 0;
        while (characters.length > charPosition) {
            if (bitOutputStream.isZero(position++)) {
                char c = readChar(position);
                position += 8;
                assertTrue(c == characters[charPosition]);
                charPosition++;
            }
        }
    }

    @Test
    public void when_writeCharactersAndReference_then_validateOutput() {
        char[] characters = new char[]{'a', 'b', 'c', 'd', 'e'};
        Pair<Short, Integer> reference = new Pair<>(((short) -5), 4);

        for (char character : characters) {
            underTest.writeCharacter(character);
        }
        underTest.writeReference(reference.getKey(), reference.getValue());

        int position = 0;
        int itemCount = 0;
        while (itemCount < 6) {
            if (bitOutputStream.isZero(position)) {
                position++;
                char c = readChar(position);
                position += 8;
                assertEquals(c, characters[itemCount]);
            } else if (bitOutputStream.isOne(position)){
                position++;
                Pair<Short, Integer> ref = readReference(position);
                position += 22;
                assertEquals(reference, ref);
            }
            itemCount++;
        }
    }

    private Pair<Short, Integer> readReference(int position) {
        short c = 0;
        for (int i = 0; i < 16; i++) {
            c = (short) ((c << 1) | (bitOutputStream.isOne(position++) ? 1 : 0));
        }
        int length = 0;
        for (int i = 0; i < 6; i++) {
            length = ((length << 1) | (bitOutputStream.isOne(position++) ? 1 : 0));
        }
        length += 3;
        return new Pair<>(c, length);
    }

    private char readChar(int position) {
        byte c = 0;
        for (int i = 0; i < 8; i++) {
            c = (byte) ((c << 1) | (bitOutputStream.isOne(position++) ? 1 : 0));
        }
        return (char) c;
    }

    private static class MockBitOutputStream implements BitOutputStream {

        final ArrayList<Boolean> bits = new ArrayList<>();

        @Override
        public void writeBit(boolean bit) {
            bits.add(bit);
        }

        @Override
        public void close() {
            // no-op
        }

        boolean isOne(int index) {
            return bits.get(index);
        }

        boolean isZero(int index) {
            return !bits.get(index);
        }
    }
}
