package com.giljulio.crunch.text.sample;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.StringReader;
import com.giljulio.crunch.text.compressor.writer.StringWriter;

public class CustomConfigurationSample {

    private static final String ANCIENT_EGYPTIAN_TEXT = "Ancient Egyptian literature was written in the Egyptian language " +
            "from Ancient Egypt's pharaonic period until the end of Roman domination. Along with Sumerian literature, it " +
            "is considered the world's earliest literature. Writing in Ancient Egypt first appeared in the late 4th mille" +
            "nnium BC. By the Old Kingdom, literary works included funerary texts, epistles and letters, religious hymns " +
            "and poems, and commemorative autobiographical texts. It was not until the early Middle Kingdom that a narrat" +
            "ive Egyptian literature was created. Middle Egyptian, the spoken language of the Middle Kingdom, became a cl" +
            "assical language during the New Kingdom, when the vernacular language known as Late Egyptian first appeared " +
            "in writing. Scribes of the New Kingdom canonized and copied many literary texts written in Middle Egyptian, " +
            "which remained the language used for oral readings of sacred hieroglyphic texts. Ancient Egyptian literature" +
            " has been preserved on a wide variety of media, including papyrus scrolls and packets, limestone or ceramic " +
            "ostraca, wooden writing boards, monumental stone edifices and coffins. Hidden caches of literature, buried f" +
            "or thousands of years, have been discovered in settlements on the dry desert margins of Egyptian civilization.";

    public static void main(String[] args) {

        Crunch crunch = new Crunch.Builder()
                .minimumCharacterReferenceLength(10)
                .searchCharacterBufferSize(512)
                .build();

        String compressedOutput = crunch.compress(new StringReader(ANCIENT_EGYPTIAN_TEXT), new StringWriter());

        System.out.println(ANCIENT_EGYPTIAN_TEXT);
        System.out.println(compressedOutput);
    }
}
