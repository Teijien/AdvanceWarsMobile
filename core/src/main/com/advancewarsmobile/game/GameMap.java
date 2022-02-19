package com.advancewarsmobile.game;

public class GameMap implements Map {
    Unit[][] map;

    public GameMap(int cols, int rows) {
        map = new Unit[cols][rows];
    }

    public void moveUnit(ICell origin, ICell destination) {
        validateCells(origin, destination);

        setUnit(getUnit(origin.x(), origin.y()), destination.x(), destination.y());
        deleteUnit(origin.x(), origin.y());
    }

    public boolean unitHere(int col, int row) { return map[col][row] != null; }

    public Unit getUnit(int col, int row) { return map[col][row]; }

    public void setUnit(Unit unit, int col, int row) {
        map[col][row] = unit;
    }

    public void deleteUnit(int col, int row) {
        map[col][row] = null;
    }

    public void battleUnits(ICell origin, ICell target) {
        Unit attacker = getUnit(origin.x(), origin.y());
        Unit defender = getUnit(target.x(), target.y());

        attacker.attackUnit(defender);

        if (attacker.hp() <= 0) {
            deleteUnit(origin.x(), origin.y());
        }

        if (defender.hp() <= 0) {
            deleteUnit(target.x(), target.y());
        }
    }

    private void validateCells(ICell origin, ICell destination) {
        if (!unitHere(origin.x(), origin.y()))
            throw new NullPointerException("No unit at origin");
        if (unitHere(destination.x(), destination.y()))
            throw new IllegalStateException("Cannot move unit to filled space");
    }
}
