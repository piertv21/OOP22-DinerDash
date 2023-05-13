package it.unibo.dinerdash;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;

final class ImageReaderWithCacheTest {

    private static ImageReaderWithCache imageReaderWithCache;

    @BeforeAll
    static void init() {
        imageReaderWithCache = new ImageReaderWithCache(Constants.ROOT);
    }

    @BeforeEach
    void setUp() {
        imageReaderWithCache.readImage("background.jpg");
    }

    @Test
    void testReadImage() {
        assertThrows(NullPointerException.class, () -> {
            imageReaderWithCache.readImage("randomImageName.jpg");
        });
        assertNotNull(imageReaderWithCache.readImage("chef.gif"));
    }

    @Test
    void testGetCachedImage() {
        assertNull(imageReaderWithCache.getCachedImage("randomImageName"));
        assertNotNull(imageReaderWithCache.getCachedImage("background"));
    }

    @Test
    void testClearCache() {
        assertNotNull(imageReaderWithCache.getCachedImage("background"));
        imageReaderWithCache.clearCache();
        assertNull(imageReaderWithCache.getCachedImage("background"));
    }

}
