package com.yohann;

import com.yohann.models.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreasureTest {

    @Test
    void shouldSetQuantityToOneIfItsZero() {
        Treasure treasure = new Treasure(0, 0, 0);
        assertTrue(treasure.hasTreasureLeft());
    }
}
