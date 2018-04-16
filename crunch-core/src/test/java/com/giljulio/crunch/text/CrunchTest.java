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
    public void given_compress_when_inputContainsNonAlphabetCharacters_expectCharacters() {
        String output = underTest.compress("a\tb\tc\td\te\tf\tg\th\ti\tj\tk\tl\tm\tn\to\tp\tq\tr\ts\tt\tu\tv\tw\tx\ty\tz\n");

        assertEquals("a\tb\tc\td\te\tf\tg\th\ti\tj\tk\tl\tm\tn\to\tp\tq\tr\ts\tt\tu\tv\tw\tx\ty\tz\n", output);
    }

    @Test
    public void given_compress_when_inputContainsNoReferences_expectCharacters() {
        String output = underTest.compress("abcdefghijk");

        assertEquals("abcdefghijk", output);
    }

    @Test
    public void given_compress_when_emptyInput_expectCharacters() {
        String output = underTest.compress("");

        assertEquals("", output);
    }

    @Test
    public void given_compress_when_smallInput_expectCharacters() {
        String output = underTest.compress("The mission of the Wikimedia Foundation is to empower and engage people around the world to collect and develop educational content under a free license or in the public domain, and to disseminate it effectively and globally.");

        assertEquals("The mission of t(he )Wikimedia Foundat(ion )is to empower and engage people ar(ound)( the )worl(d t)o collect( and )develop educ(ation)al( co)ntent (und)(er a) free license or in( the )pub(lic) domain,( and )(to )d(iss)eminate it eff(ect)i(vel)y( and )globally.", output);
    }

    @Test
    public void given_compress_when_smallBuffer_compressedOutput() {
        Crunch underTest = new Crunch.Builder()
                .searchCharacterBufferSize(27)
                .build();

        String output = underTest.compress("The mission of the Wikimedia Foundation is to empower and engage people around the world to collect and develop educational content under a free license or in the public domain, and to disseminate it effectively and globally.");

        assertEquals("The mission of t(he )Wikimedia Foundation is to empower and engage people arou(nd )the worl(d t)o collect a(nd )develop educational content under a free license or in the pub(lic) domain, and to disseminate it effectively and globally.", output);
    }
}