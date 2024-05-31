package com.yohann;

import com.yohann.models.TreasureMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TreasureMapTest {
    @Test
    void shouldThrowErrorIfMapSizeIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new TreasureMap(0, 0));
    }
}
