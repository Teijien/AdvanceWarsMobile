package com.advancewarsmobile.game;

public class GameMap implements Map {
    Unit[][] map;

    public GameMap(int cols, int rows) {
        map = new Unit[cols][rows];
    }

    @Override
    public void moveUnit(ICell origin, ICell destination) {
        validateCells(origin, destination);

        setUnit(getUnit(origin.x(), origin.y()), destination.x(), destination.y());
        deleteUnit(origin.x(), origin.y());
    }

    @Override
    public boolean unitHere(int col, int row) { return map[col][row] != null; }

    @Override
    public Unit getUnit(int col, int row) { return map[col][row]; }

    @Override
    public void setUnit(Unit unit, int col, int row) {
        map[col][row] = unit;
    }

    @Override
    public void deleteUnit(int col, int row) {
        map[col][row] = null;
    }

    private void validateCells(ICell origin, ICell destination) {
        if (!unitHere(origin.x(), origin.y()))
            throw new NullPointerException("No unit at origin");
        if (unitHere(destination.x(), destination.y()))
            throw new IllegalStateException("Cannot move unit to filled space");
    }
}
