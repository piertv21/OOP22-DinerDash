package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.api.ImageReader;
import it.unibo.dinerdash.utility.impl.ImageReaderImpl;

final class ImageReaderTest {

    private static ImageReader imageReader;

    @BeforeAll
    static void init() {
        imageReader = new ImageReaderImpl(Constants.ROOT);
    }

    @Test
    void testReadImage() {
        assertThrows(NullPointerException.class, () -> {
            imageReader.readImage("randomImageName.jpg");
        });
        assertNotNull(imageReader.readImage("background.jpg"));
    }

}
