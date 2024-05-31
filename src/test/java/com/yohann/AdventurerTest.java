package com.yohann;

import com.yohann.enums.Orientation;
import com.yohann.models.Adventurer;
import com.yohann.models.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerTest {
    @Test
    void shouldPickOneTreasure() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        Treasure treasure = new Treasure(0, 0, 1);
        assertTrue(adventurer.pickUp(treasure));
    }

    @Test
    void cantPickTreasureBecauseTooFar() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        Treasure treasure = new Treasure(1, 0, 1);
        assertFalse(adventurer.pickUp(treasure));
    }

    @Test
    void cantPickTreasureBecauseThereIsNoMoreLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        Treasure treasure = new Treasure(0, 0, 0);
        assertFalse(adventurer.pickUp(treasure));
    }

    @Test
    void adventurersOrientationGoFromSouthToWestToTurnRight() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "D");
        adventurer.turnRight();

        assertEquals(Orientation.OUEST, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromWestToNorthToTurnRight() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.OUEST, "D");
        adventurer.turnRight();

        assertEquals(Orientation.NORD, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromNorthToEastToTurnRight() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.NORD, "D");
        adventurer.turnRight();

        assertEquals(Orientation.EST, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromEastToSouthToTurnRight() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.EST, "D");
        adventurer.turnRight();

        assertEquals(Orientation.SUD, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromSouthToEastToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "D");
        adventurer.turnLeft();

        assertEquals(Orientation.EST, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromEastToNorthToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.EST, "D");
        adventurer.turnLeft();

        assertEquals(Orientation.NORD, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromNorthToWestToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.NORD, "D");
        adventurer.turnLeft();

        assertEquals(Orientation.OUEST, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromWestToSouthToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.OUEST, "D");
        adventurer.turnLeft();

        assertEquals(Orientation.SUD, adventurer.getOrientation());
    }

//    @Test
//    void adventurerShouldMoveOnlyIfCurrenteMoveIsLetterA() {
//        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.OUEST, "D");
//        TMap map = new TMap(1, 1);
//        assertFalse(adventurer.moveForward(map));
//
//    }
//    @Test
//    void shouldMoveDownOnTheMapBecauseOfSouthOrientation() {
//        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
//        TMap baseMap = new TMap(2, 2);
//        baseMap.addOnMap(adventurer);
//        adventurer.moveForward(baseMap);
//
//        assertEquals(baseMap.getMap().get(0).get(1), adventurer);
//    }
}
