package com.yohann;

import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.enums.Orientation;
import com.yohann.enums.Type;
import com.yohann.exceptions.MultipleTreasureMapSizeException;
import com.yohann.models.TreasureMap;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    File resourcesDirectory = new File("src/test/resources");
    File saveFolder = new File("src/test/results");

    @Test
    void shouldThrowAnErrorIfPassedArgIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Engine(null));
    }

    @Test
    void shouldThrowAnErrorIfFileDoesntExists() {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "data"));

        assertThrows(
                FileNotFoundException.class,
                engine::readFileContent);
    }

    @Test
    void shouldReturnFileContentAsList() throws FileNotFoundException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<String> expected = Arrays.asList(
                "C - 3 - 4",
                "M - 1 - 0",
                "M - 2 - 1",
                "T - 0 - 3 - 2",
                "T - 1 - 3 - 3",
                "A - Lara - 1 - 1 - S - AADADAGGA"
        );

        assertEquals(expected, engine.readFileContent());
    }

    @Test
    void shouldReturnFileContentAsListWithoutComments() throws FileNotFoundException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputWithComments.txt"));
        List<String> expected = Arrays.asList(
                "C - 3 - 4",
                "M - 1 - 0",
                "M - 2 - 1",
                "T - 0 - 3 - 2",
                "T - 1 - 3 - 3",
                "A - Lara - 1 - 1 - S - AADADAGGA"
        );

        assertEquals(expected, engine.readFileContent());
    }

    @Test
    void shouldThrowAnErrorIfMultipleTreasureMapSizeAreFound() throws MultipleTreasureMapSizeException {
        Engine engine = new Engine(Paths.get("src"));

        assertThrows(
                MultipleTreasureMapSizeException.class,
                () -> engine.generateMap(Arrays.asList("C-5-5", "C-3-7")));
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
        Engine engine = new Engine(Paths.get("src"));

        assertEquals(new Adventurer("Yohann", "0", "0", "S", "ADDADDADD"), engine.dataMapping(new String[] {"A", "Yohann", "0", "0", "S", "ADDADDADD"}));
    }

    @Test
    void shouldMapAdventurerStringDataToTreasureClass() {
        Engine engine = new Engine(Paths.get("src"));

        assertEquals(new Treasure("0", "1", "5"), engine.dataMapping(new String[] {"T", "0", "1", "5"}));
    }

    @Test
    void shouldReturnA3x4PlaineList() throws FileNotFoundException, MultipleTreasureMapSizeException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "plaine3x4.txt"));
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

        assertArrayEquals(expected.toArray(), engine.generateMap().getMap().toArray());
    }

    @Test
    void shouldAddAMountainAt1x0y() throws FileNotFoundException, MultipleTreasureMapSizeException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "moutain1x0y.txt"));
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

        assertArrayEquals(expected.toArray(), engine.generateMap().getMap().toArray());
    }

    @Test
    void shouldPlaceEverythingOnTheRightPlace() throws FileNotFoundException, MultipleTreasureMapSizeException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<List<Case>> expected = Arrays.asList(
            Arrays.asList(
                new Case(Type.PLAINE, 0, 0),
                new Case(Type.PLAINE, 0, 1),
                new Case(Type.PLAINE, 0, 2),
                new Treasure(0, 3, 2)
            ),
            Arrays.asList(
                new Case(Type.MONTAGNE, 1, 0),
                new Case(Type.PLAINE, 1, 1),
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

        assertArrayEquals(expected.toArray(), engine.generateMap().getMap().toArray());
    }

    @Test
    void adventurersAreStored() throws FileNotFoundException, MultipleTreasureMapSizeException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<Case> expected = List.of(new Adventurer("Lara", 1, 1, Orientation.SUD, "AADADAGGA"));
        TreasureMap map = engine.generateMap();
        assertEquals(expected, map.getAdventurers());

    }

    @Test
    void shouldFailBecauseDataDoesntMatchExpected() throws FileNotFoundException, MultipleTreasureMapSizeException {
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));
        List<List<Case>> expected = Arrays.asList(
                Arrays.asList(
                        new Case(Type.PLAINE, 0, 0),
                        new Case(Type.PLAINE, 0, 1),
                        new Case(Type.PLAINE, 0, 2),
                        new Treasure(0, 3, 2)
                ),
                Arrays.asList(
                        new Case(Type.MONTAGNE, 1, 0),
                        new Case(Type.PLAINE, 1, 2),
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

        assertFalse(Arrays.equals(expected.toArray(), engine.generateMap().getMap().toArray()));
    }

    @Test
    void shouldToStringAdventureInTheRightFormat() {
        assertEquals("A - Tuna - 0 - 0 - S - 0", (new Adventurer("Tuna", 0, 0, Orientation.SUD, "A")).toString());
    }

    @Test
    void shouldToStringTreasureInTheRightFormat() {
        assertEquals("T - 1 - 2 - 3", (new Treasure(1, 2, 3)).toString());
    }

    @Test
    void shouldSaveTheResultAndRespectFormat() throws IOException, MultipleTreasureMapSizeException {
        String resultFileName = String.format("result_%s", new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date()));

        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputNoComments.txt"));

        List<String> expectedFileContent = engine.readFileContent();
        engine.save(engine.generateMap(expectedFileContent), Paths.get("src", "test" , "results", resultFileName));

        engine = new Engine(Paths.get( "src", "test", "results", resultFileName));
        List<String> fileContent = engine.readFileContent();

        expectedFileContent = expectedFileContent
                .stream()
                .map(this::removeLastAdventurerData)
                .collect(Collectors.toList());

        fileContent = fileContent
                .stream()
                .map(this::removeLastAdventurerData)
                .collect(Collectors.toList());

        assertEquals(expectedFileContent, fileContent);
    }

    private String removeLastAdventurerData(String el) {
        if(!el.startsWith("A")) return el;
        List<String> list = new ArrayList<>(Arrays.asList(el.replaceAll("\\s*-\\s*", "-").split("-")));
        list.remove(list.size() - 1);
        return String.join(" - ", list);
    }

    @Test
    void adventurerShouldMoveForwardAndPickUpTreasure() {
        Engine engine = new Engine(Paths.get("src"));
        TreasureMap map = new TreasureMap(1, 2);
        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.SUD, "A"));
        map.addOnMap(new Treasure(0, 1, 1));

        engine.run(map);

        assertEquals(1, map.getAdventurers().get(0).getNumberOfTreasures());
    }

    @Test
    void adventurerShouldTurnThenWalkThenPickupATreasure() {
        Engine engine = new Engine(Paths.get("src"));
        TreasureMap map = new TreasureMap(2, 1);
        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.SUD, "GA"));
        map.addOnMap(new Treasure(1, 0, 1));

        engine.run(map);

        assertEquals(1, map.getAdventurers().get(0).getNumberOfTreasures());
    }

    @Test
    void adventurerShouldTurn2TimesThenWalk() {
        Engine engine = new Engine(Paths.get("src"));
        TreasureMap map = new TreasureMap(2, 1);
        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.OUEST, "DDA"));

        engine.run(map);

        assertEquals(map.getAt(1, 0), new Adventurer("Tuna", 1, 0, Orientation.EST, "DDA"));
    }

    @Test
    void secondAdventurerAddedCantMoveWhereFirstIs() {
        Engine engine = new Engine(Paths.get("src"));
        TreasureMap map = new TreasureMap(3, 1);

        map.addOnMap(new Adventurer("Tuna", 0, 0, Orientation.EST, "A"));
        map.addOnMap(new Adventurer("Lana", 2, 0, Orientation.SUD, "DA"));

        engine.run(map);

        assertEquals(((Adventurer)map.getAt(2, 0)).getName(), "Lana");
    }

    @Test
    void adventurerDoesntPickUpIfHeDoesntMoveForward() {
        Engine engine = new Engine(Paths.get("src"));
        TreasureMap map = new TreasureMap(1,1);
        Adventurer tuna = new Adventurer("Tuna", 0, 0, Orientation.EST, "DGD");
        Treasure treasure = new Treasure(0, 0, 1);
        map.addOnMap(tuna);
        map.addOnMap(treasure);

        engine.run(map);

        assertTrue(treasure.hasTreasureLeft());
    }

    @Test
    void adventurerGetStuckToMountainButKeepGoingAfter() {
        Engine engine = new Engine(Paths.get("src"));
        TreasureMap map = new TreasureMap(2,2);
        Adventurer tuna = new Adventurer("Tuna", 0, 0, Orientation.SUD, "AGADA");
        map.addOnMap(tuna);
        map.addOnMap(new Case(Type.MONTAGNE, 0, 1));

        engine.run(map);

        assertTrue(tuna.getX() == 1 && tuna.getY() == 1);
    }

    @Test
    void assertThatTheWholeProcessWorks() throws IOException, MultipleTreasureMapSizeException {
        String resultFileName = String.format("result_%s", new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date()));
        Engine engine = new Engine(Paths.get(resourcesDirectory.toString(), "baseInputWithComments.txt"));
        TreasureMap map = engine.generateMap();
        engine.run(map);
        engine.save(map, Paths.get(saveFolder.toString(), resultFileName));

        engine = new Engine(Paths.get( "src", "test", "results", resultFileName));
        List<String> fileContent = engine.readFileContent();

        fileContent = fileContent
                .stream()
                .map(this::removeLastAdventurerData)
                .collect(Collectors.toList());

        List<String> expectedFileContent = List.of(
                "C - 3 - 4",
                "M - 1 - 0",
                "M - 2 - 1",
                "T - 1 - 3 - 2",
                "A - Lara - 0 - 3 - S"
        );

        assertEquals(expectedFileContent, fileContent);
    }

}
