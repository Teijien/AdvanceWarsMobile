package com.advancewarsmobile.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameMapTest {
    Map map;
    Unit unit = new BasicUnit(0, new BasicStats(1, 1, 1, 1, 1, 1));

    @BeforeEach
    public void init() {
        map = new GameMap(6, 8);
    }

    @Test
    public void setUnit() {
        map.setUnit(unit, 0, 0);
        Assertions.assertSame(unit, map.getUnit(0, 0));
    }

    @Test
    public void accessOutOfBounds() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                map.setUnit(unit, -1, 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                map.setUnit(unit, 0, -1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                map.getUnit(-1, 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                map.getUnit(0, -1));
    }

    @Test
    public void deleteUnit() {
        map.setUnit(unit, 0, 0);
        map.deleteUnit(0, 0);
        Assertions.assertNull(map.getUnit(0, 0));
    }

    @Test
    public void moveUnit() {
        map.setUnit(unit, 0, 0);
        map.moveUnit(new Cell(0, 0), new Cell(1, 1));
        Assertions.assertNull(map.getUnit(0, 0));
        Assertions.assertSame(unit, map.getUnit(1, 1));
    }
}
