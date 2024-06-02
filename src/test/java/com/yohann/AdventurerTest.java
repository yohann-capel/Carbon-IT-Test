package com.yohann;

import com.yohann.enums.Move;
import com.yohann.enums.Orientation;
import com.yohann.enums.Type;
import com.yohann.exceptions.WrongMoveException;
import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.models.TreasureMap;
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
        Treasure treasure = new Treasure(0, 0, 1);
        adventurer.pickUp(treasure);

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
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "G");
        adventurer.turnLeft();

        assertEquals(Orientation.EST, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromEastToNorthToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.EST, "G");
        adventurer.turnLeft();

        assertEquals(Orientation.NORD, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromNorthToWestToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.NORD, "G");
        adventurer.turnLeft();

        assertEquals(Orientation.OUEST, adventurer.getOrientation());
    }

    @Test
    void adventurersOrientationGoFromWestToSouthToTurnLeft() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.OUEST, "G");
        adventurer.turnLeft();

        assertEquals(Orientation.SUD, adventurer.getOrientation());
    }

    @Test
    void shouldThrowAnExceptionIfCurrentMoveIsNotLetterA() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "D");

        assertThrows(WrongMoveException.class, () -> adventurer.moveForward(new TreasureMap(2, 2)));
    }

    @Test
    void shouldThrowAnExceptionIfCurrentMoveIsNotLetterDWhenTurningRight() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "G");

        assertThrows(WrongMoveException.class, adventurer::turnRight);
    }

    @Test
    void adventurerNextMoveShouldChangeAndBeTheNextOneWhenMovingForward() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "AA");
        adventurer.moveForward(new TreasureMap(1,1));

        assertEquals(Move.AVANCER, adventurer.getCurrentMove());
    }

    @Test
    void adventurerShouldMoveForwardSouth() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        TreasureMap map = new TreasureMap(1,2);
        map.addOnMap(adventurer);
        adventurer.moveForward(map);

        assertEquals(1, adventurer.getY());
    }

    @Test
    void adventurerShouldMoveForwardEast() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.EST, "A");
        TreasureMap map = new TreasureMap(2,1);
        map.addOnMap(adventurer);
        adventurer.moveForward(map);

        assertEquals(1, adventurer.getX());
    }

    @Test
    void shouldStuckOnAMountain() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        TreasureMap map = new TreasureMap(1,2);
        map.addOnMap(adventurer);
        map.addOnMap(new Case(Type.MONTAGNE, 0, 1));
        adventurer.moveForward(map);

        assertEquals(0, adventurer.getY());
    }

    @Test
    void shouldStuckOnAnAdventurer() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "A");
        TreasureMap map = new TreasureMap(1,2);
        map.addOnMap(adventurer);
        map.addOnMap(new Adventurer("Bobby", 0, 1, Orientation.NORD, "DAADA"));
        adventurer.moveForward(map);

        assertEquals(0, adventurer.getY());
    }

    @Test
    void automaticallyExecuteNextSetMove() {
        Adventurer adventurer = new Adventurer("Tuna", 0, 0, Orientation.SUD, "DA");
        TreasureMap map = new TreasureMap(1, 1);
        adventurer.doCurrentMove(map);

        assertEquals(Orientation.OUEST, adventurer.getOrientation());
    }


}
