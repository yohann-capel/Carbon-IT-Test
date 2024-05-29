package com.yohann;

import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.enums.Orientation;
import com.yohann.enums.Type;
import com.yohann.exceptions.MultipleTreasureMapSizeException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreasureMapTest {
    File resourcesDirectory = new File("src/test/resources");

    @Test
    void shouldThrowAnErrorIfPassedArgIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new TreasureMap(null));
    }

    @Test
    void shouldThrowAnErrorIfFileDoesntExists() {
        TreasureMap treasureMap = new TreasureMap(Paths.get(resourcesDirectory.toString(), "data"));
        assertThrows(
                FileNotFoundException.class,
                treasureMap::readFileContent);
    }

    @Test
    void shouldReturnFileContentAsList() throws FileNotFoundException {
        TreasureMap tm = new TreasureMap(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<String> expected = Arrays.asList(
                "C - 3 - 4",
                "M - 1 - 0",
                "M - 2 - 1",
                "T - 0 - 3 - 2",
                "T - 1 - 3 - 3",
                "A - Lara - 1 - 1 - S - AADADAGGA"
        );
        assertEquals(expected, tm.readFileContent());
    }

    @Test
    void shouldReturnFileContentAsListWithoutComments() throws FileNotFoundException {
        TreasureMap tm = new TreasureMap(Paths.get(resourcesDirectory.toString(), "baseInputWithComments.txt"));
        List<String> expected = Arrays.asList(
                "C - 3 - 4",
                "M - 1 - 0",
                "M - 2 - 1",
                "T - 0 - 3 - 2",
                "T - 1 - 3 - 3",
                "A - Lara - 1 - 1 - S - AADADAGGA"
        );
        assertEquals(expected, tm.readFileContent());
    }

    @Test
    void shouldThrowAnErrorIfMultipleTreasureMapSizeAreFound() throws MultipleTreasureMapSizeException {
        TreasureMap tm = new TreasureMap(Paths.get("src"));
        assertThrows(
                MultipleTreasureMapSizeException.class,
                () -> tm.generateMap(Arrays.asList("C-5-5", "C-3-7")));
    }

    @Test
    void shouldReturnRightTypeBasedOnString() {
        assertEquals(Type.MONTAGNE, Type.retrieveByLetter("M"));
    }

    @Test
    void shouldThrowAnErrorIfTypeAskedDoesntExists() {
        assertThrows(IllegalArgumentException.class, () -> Type.retrieveByLetter("Z"));
    }

    @Test
    void shouldReturnOneMountainEntity() {
        assertEquals(new Case(Type.MONTAGNE, 0, 0), new Case(Type.retrieveByLetter("M"), 0, 0));
    }

    @Test
    void shouldMapAdventurerStringDataToAdventurerClass() {
        TreasureMap tm = new TreasureMap(Paths.get("src"));
        assertEquals(new Adventurer("Yohann", "0", "0", "S", "ADDADDADD"), tm.mapData(new String[] {"A", "Yohann", "0", "0", "S", "ADDADDADD"}));
    }

    @Test
    void shouldMapAdventurerStringDataToTreasureClass() {
        TreasureMap tm = new TreasureMap(Paths.get("src"));
        assertEquals(new Treasure("0", "1", "5"), tm.mapData(new String[] {"T", "0", "1", "5"}));
    }

    @Test
    void shouldReturnA3x4PlaineList() throws FileNotFoundException, MultipleTreasureMapSizeException {
        TreasureMap tm = new TreasureMap(Paths.get(resourcesDirectory.toString(), "plaine3x4.txt"));
        List<List<Case>> expected = Arrays.asList(
            Arrays.asList(
                new Case(Type.PLAINE, 0, 0),
                new Case(Type.PLAINE, 0, 1),
                new Case(Type.PLAINE, 0, 2),
                new Case(Type.PLAINE, 0, 3)
            ),
            Arrays.asList(
                new Case(Type.PLAINE, 1, 0),
                new Case(Type.PLAINE, 1, 1),
                new Case(Type.PLAINE, 1, 2),
                new Case(Type.PLAINE, 1, 3)
            ),
            Arrays.asList(
                new Case(Type.PLAINE, 2, 0),
                new Case(Type.PLAINE, 2, 1),
                new Case(Type.PLAINE, 2, 2),
                new Case(Type.PLAINE, 2, 3)
            )
        );
        assertArrayEquals(expected.toArray(), tm.generateMap().toArray());
    }

    @Test
    void shouldAddAMountainAt1x0y() throws FileNotFoundException, MultipleTreasureMapSizeException {
        TreasureMap tm = new TreasureMap(Paths.get(resourcesDirectory.toString(), "moutain1x0y.txt"));
        List<List<Case>> expected = Arrays.asList(
            Arrays.asList(
                new Case(Type.PLAINE, 0, 0),
                new Case(Type.PLAINE, 0, 1)
            ),
            Arrays.asList(
                new Case(Type.MONTAGNE, 1, 0),
                new Case(Type.PLAINE, 1, 1)
            )
        );
        assertArrayEquals(expected.toArray(), tm.generateMap().toArray());
    }

    @Test
    void shouldPlaceEverythingOnTheRightPlace() throws FileNotFoundException, MultipleTreasureMapSizeException {
        TreasureMap tm = new TreasureMap(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<List<Case>> expected = Arrays.asList(
            Arrays.asList(
                new Case(Type.PLAINE, 0, 0),
                new Case(Type.PLAINE, 0, 1),
                new Case(Type.PLAINE, 0, 2),
                new Treasure(0, 3, 2)
            ),
            Arrays.asList(
                new Case(Type.MONTAGNE, 1, 0),
                new Adventurer("Lara", 1, 1, Orientation.SUD, "AADADAGGA"),
                new Case(Type.PLAINE, 1, 2),
                new Treasure(1, 3, 3)
            ),
            Arrays.asList(
                new Case(Type.PLAINE, 2, 0),
                new Case(Type.MONTAGNE, 2, 1),
                new Case(Type.PLAINE, 2, 2),
                new Case(Type.PLAINE, 2, 3)
            )
        );

        assertArrayEquals(expected.toArray(), tm.generateMap().toArray());
    }

    @Test
    void shouldFailBecauseDataDoesntMatchExpected() throws FileNotFoundException, MultipleTreasureMapSizeException {
        TreasureMap tm = new TreasureMap(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<List<Case>> expected = Arrays.asList(
                Arrays.asList(
                        new Case(Type.PLAINE, 0, 0),
                        new Case(Type.PLAINE, 0, 1),
                        new Case(Type.PLAINE, 0, 2),
                        new Treasure(0, 3, 2)
                ),
                Arrays.asList(
                        new Case(Type.MONTAGNE, 1, 0),
                        new Adventurer("Lara", 1, 1, Orientation.SUD, "AADADAGGA"),
                        new Case(Type.PLAINE, 1, 2),
                        new Treasure(1, 3, 3)
                ),
                Arrays.asList(
                        new Case(Type.PLAINE, 2, 0),
                        new Case(Type.MONTAGNE, 2, 1),
                        new Case(Type.MONTAGNE, 2, 2),
                        new Case(Type.PLAINE, 2, 3)
                )
        );

        assertFalse(Arrays.equals(expected.toArray(), tm.generateMap().toArray()));
    }
}
