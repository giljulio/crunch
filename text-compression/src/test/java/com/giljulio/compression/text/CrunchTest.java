package com.giljulio.compression.text;

import com.giljulio.compression.text.decompressor.model.ReaderItem;
import com.giljulio.compression.text.decompressor.model.ReaderItem.Character;
import com.giljulio.compression.text.decompressor.model.ReaderItem.Reference;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CrunchTest {

    @Test
    public void when_uncompressCharacters_validateOutput() {
        ReaderItem[] items = new ReaderItem[]{new Character('a'), new Character('b'), new Character('c')};
        Crunch underTest = new Crunch.Builder().build();

        String output = underTest.decompress(new MockDecompressReader(items));

        assertEquals("abc", output);
    }

    @Test
    public void when_uncompressCharactersAndReferences_validateOutput() {
        ReaderItem[] items = new ReaderItem[]{
                new Character('a'), new Character('b'), new Character('c'),
                new Character('d'), new Character('e'), new Reference(-5, 3)};
        Crunch underTest = new Crunch.Builder().build();

        String output = underTest.decompress(new MockDecompressReader(items));

        assertEquals("abcdeabc", output);
    }

}