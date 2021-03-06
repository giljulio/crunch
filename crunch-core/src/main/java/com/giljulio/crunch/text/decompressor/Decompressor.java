package com.giljulio.crunch.text.decompressor;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.decompressor.model.ReaderItem;
import com.giljulio.crunch.text.decompressor.reader.DecompressReader;
import com.giljulio.crunch.text.decompressor.writer.DecompressWriter;
import com.giljulio.crunch.text.util.CircularQueue;

public final class Decompressor<T> {

    private final Crunch crunch;
    private final DecompressReader reader;
    private final DecompressWriter<T> writer;

    private final CircularQueue<Character> characterBuffer;
    private int characterBufferStartIndex = 0;

    public Decompressor(Crunch crunch, DecompressReader reader, DecompressWriter<T> writer) {
        this.crunch = crunch;
        this.reader = reader;
        this.writer = writer;
        this.characterBuffer = new CircularQueue<>(crunch.getBufferSize());
    }

    public T execute() {
        int position = 0;
        while (true) {
            ReaderItem readerItem = reader.read();
            if (readerItem instanceof ReaderItem.Character) {
                ReaderItem.Character item = (ReaderItem.Character) readerItem;
                write(item.getCharacter());
                position++;
            } else if (readerItem instanceof ReaderItem.Reference) {
                ReaderItem.Reference item = (ReaderItem.Reference) readerItem;
                int startIndex = position + item.getOffset();
                for (int i = startIndex; i < startIndex + item.getLength(); i++) {
                    int index = i - characterBufferStartIndex;
                    write(characterBuffer.get(index));
                    position++;
                }
            } else {
                return writer.output();
            }
        }
    }

    private void write(Character character) {
        trimBuffer();
        characterBuffer.add(character);
        writer.write(character);
    }

    private void trimBuffer() {
        while (characterBuffer.size() >= crunch.getBufferSize()) {
            characterBuffer.removeLast();
            characterBufferStartIndex++;
        }
    }

}