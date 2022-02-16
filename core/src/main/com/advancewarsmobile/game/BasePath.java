package com.advancewarsmobile.game;

import java.util.LinkedList;
import java.util.List;

/* Model for tracking the path taken by the current unit being moved */
public class BasePath implements Path {
    List<ICell> path;

    public BasePath(List<ICell> path) {
        this.path = path;
    }

    /* Create a new path starting with the input cell */
    public void initializeNewPath(ICell cell) {
        path.clear();
        path.add(cell);
    }

    /* Removes all cells that come after the input cell in the path */
    public boolean removeCellsAfter(ICell cell) {
        if (!path.contains(cell)) { return false; }

        while (path.indexOf(cell) < path.size() - 1) {
            path.remove(path.size() - 1);
        }

        return true;
    }

    public ICell getFirst() {
        return path.get(0);
    }

    public ICell getLast() {
        return path.get(path.size() - 1);
    }

    public int size() {
        return path.size();
    }

    public void add(ICell cell) {
        path.add(cell);
    }

    /* Check if there is a possible path from the origin cell to the target cell.
     * Sets the path to the new path if possible. Otherwise, the path is kept the same. */
    public void recalculatePath(ICell cell, int mov) {
        LinkedList<ICell> temp = new LinkedList<>();
        temp.add(path.get(0));

        /* Iterate on the x-axis or y-axis until the unit's mov is exhausted,
         * or the target cell is reached */
        while (temp.size() - 1 < mov) {
            if (temp.getLast().equals(cell)) { break; }

            if (temp.getLast().x() > cell.x()) {
                temp.add(new Cell(temp.getLast().x() - 1, temp.getLast().y()));
            } else if (temp.getLast().x() < cell.x()) {
                temp.add(new Cell(temp.getLast().x() + 1, temp.getLast().y()));
            } else if (temp.getLast().y() > cell.y()) {
                temp.add(new Cell(temp.getLast().x(), temp.getLast().y() - 1));
            } else if (temp.getLast().y() < cell.y()) {
                temp.add(new Cell(temp.getLast().x(), temp.getLast().y() + 1));
            }
        }

        if (!temp.getLast().equals(cell)) {
            temp = null;
        }

        validatePath(temp);
    }

    /* Validates if a given path is null or not. Used as a helper function in recalculatePath(). */
    private void validatePath(List<ICell> path) {
        if (path == null) {
            System.out.println("Not reachable");
        } else {
            this.path = path;
            System.out.println("Recalculated path");
        }
    }
}
