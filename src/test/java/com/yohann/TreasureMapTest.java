package com.yohann;

import com.yohann.enums.Orientation;
import com.yohann.enums.Type;
import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.models.TreasureMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TreasureMapTest {
    @Test
    void shouldThrowErrorIfMapSizeIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new TreasureMap(0, 0));
    }

    @Test
    void shouldThrowIfNegativeCoordinates() {
        TreasureMap map = new TreasureMap(1,1);
        assertThrows(IndexOutOfBoundsException.class, () -> map.getAt(-1, -1));
    }

    @Test
    void shouldThrowIfCoordinatesAreOutOfBounds() {
        TreasureMap map = new TreasureMap(1,1);
        assertThrows(IndexOutOfBoundsException.class, () -> map.getAt(1, 1));
    }

    @Test
    void shouldReturnAdventurerIfThereIsOneOverATreasure() {
        TreasureMap map = new TreasureMap(3, 3);
        map.addOnMap(new Treasure(1, 1, 1));
        map.addOnMap(new Adventurer("Lana", 1, 1, Orientation.SUD, "DAADA"));

        assertEquals(new Adventurer("Lana", 1, 1, Orientation.SUD, "DAADA"), map.getAt(1,1));
    }

    @Test
    void addOnMapShouldReturnFalseIfCoordinatesAreOutOfBounds() {
        TreasureMap map = new TreasureMap(3, 3);

        assertFalse(map.addOnMap(new Treasure(-1, 3, 3)));
    }

    @Test
    void addOnMapShouldReturnTrueIfItemAdded() {
        TreasureMap map = new TreasureMap(3, 3);
        assertTrue(map.addOnMap(new Treasure(2, 2, 1)));
    }

    @Test
    void verifyAddOnMapFunctionPutsItemOnTheRightPlace() {
        TreasureMap map = new TreasureMap(2, 2);
        map.addOnMap(new Case(Type.MONTAGNE, 0, 1));
        map.addOnMap(new Treasure(1, 0, 1));

        List<List<Case>> expected = Arrays.asList(
                Arrays.asList(
                        new Case(Type.PLAINE, 0, 0),
                        new Case(Type.MONTAGNE, 0, 1)
                ),
                Arrays.asList(
                        new Treasure(1, 0, 1),
                        new Case(Type.PLAINE, 1, 1)
                )
        );

        assertArrayEquals(expected.toArray(), map.getMap().toArray());
    }

    @Test
    void addOnMapShouldAddAdventurerIfThereIsNoMountainUnder() {
        TreasureMap map = new TreasureMap(1, 1);
        map.addOnMap(new Treasure(0, 0, 1));
        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.SUD, "A"));

        List<Adventurer> expected = List.of(new Adventurer("Tuna", 0, 0, Orientation.SUD, "A"));

        assertEquals(expected, map.getAdventurers());
    }

    @Test
    void addOnMapShouldNotAddAdventurerIfThereIsAMountain() {
        TreasureMap map = new TreasureMap(1, 1);
        map.addOnMap(new Case(Type.MONTAGNE, 0, 0));
        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.SUD, "A"));

        assertTrue(map.getAdventurers().isEmpty());
    }

    @Test
    void addOnMapShouldNotAddTreasureIfThereIsAMountain() {
        TreasureMap map = new TreasureMap(1, 1);
        map.addOnMap(new Case(Type.MONTAGNE, 0, 0));
        map.addOnMap(new Treasure(0, 0, 1));

        assertSame(map.getAt(0, 0).getType(), Type.MONTAGNE);
    }

    @Test
    void addOnMapShouldAddTreasureEventIfAdventurerAlreadyOnCoordinates() {
        TreasureMap map = new TreasureMap(1, 1);
        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.SUD, "A"));
        map.addOnMap(new Treasure(0, 0, 1));

        List<List<Case>> expected = List.of(List.of(new Treasure(0, 0, 1)));
        assertArrayEquals(expected.toArray(), map.getMap().toArray());
    }

    @Test
    void shouldGetWhatsUnderAnAdventurer() {
        TreasureMap map = new TreasureMap(1, 1);
        Treasure treasure = new Treasure(0, 0, 1);
        Adventurer tuna = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        map.addOnMap(treasure);
        map.addOnMap(tuna);
        Case c = map.getUnderAdventurer(tuna);

        assertEquals(treasure, c);
    }
}
