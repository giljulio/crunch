package com.giljulio.crunch.text;

import com.giljulio.crunch.text.decompressor.model.ReaderItem;
import com.giljulio.crunch.text.decompressor.model.ReaderItem.Character;
import com.giljulio.crunch.text.decompressor.model.ReaderItem.Reference;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class CrunchTest {

    private final Crunch underTest = new Crunch.Builder().build();

    @Test
    public void when_uncompressCharacters_validateOutput() {
        ReaderItem[] items = new ReaderItem[]{new Character('a'), new Character('b'), new Character('c')};

        String output = underTest.decompress(new MockDecompressReader(items));

        assertEquals("abc", output);
    }

    @Test
    public void when_uncompressCharactersAndReferences_validateOutput() {
        ReaderItem[] items = new ReaderItem[]{
                new Character('a'), new Character('b'), new Character('c'),
                new Character('d'), new Character('e'), new Reference((short) -5, 3)};

        String output = underTest.decompress(new MockDecompressReader(items));

        assertEquals("abcdeabc", output);
    }

    @Test
    public void given_compress_when_inputContainsNonAlphabetCharacters_expectCharacters() throws FileNotFoundException {
        String output = underTest.compress("a\tb\tc\td\te\tf\tg\th\ti\tj\tk\tl\tm\tn\to\tp\tq\tr\ts\tt\tu\tv\tw\tx\ty\tz\n");

        assertEquals("a\tb\tc\td\te\tf\tg\th\ti\tj\tk\tl\tm\tn\to\tp\tq\tr\ts\tt\tu\tv\tw\tx\ty\tz\n", output);
    }

    @Test
    public void given_compress_when_inputContainsNoReferences_expectCharacters() throws FileNotFoundException {
        String output = underTest.compress("abcdefghijk");

        assertEquals("abcdefghijk", output);
    }

    @Test
    public void given_compress_when_emptyInput_expectCharacters() throws FileNotFoundException {
        String output = underTest.compress("");

        assertEquals("", output);
    }

    @Test
    public void given_compress_when_smallInput_expectCharacters() throws FileNotFoundException {
        String output = underTest.compress("she sells sea shells on the sea shore");

        assertEquals("she sells( se)a (she)(lls )on t(he se)(a sh)ore", output);
    }
}