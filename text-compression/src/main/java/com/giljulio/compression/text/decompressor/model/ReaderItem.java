package com.giljulio.compression.text.decompressor.model;

public interface ReaderItem {

    class Character implements ReaderItem {
        private final char character;

        public Character(char character) {
            this.character = character;
        }

        public char getCharacter() {
            return character;
        }
    }

    class Reference implements ReaderItem {
        private final int offset;
        private final int length;

        public Reference(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public int getOffset() {
            return offset;
        }

        public int getLength() {
            return length;
        }
    }
}
