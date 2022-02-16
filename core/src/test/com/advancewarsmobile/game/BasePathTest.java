package com.advancewarsmobile.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class BasePathTest {
    Path path;

    @BeforeEach
    public void init() {
        path = new BasePath(new LinkedList<>());
    }

    @Test
    public void initializeNewPath() {
        Cell cell = new Cell(0, 0);
        path.initializeNewPath(cell);
        Assertions.assertEquals(1, path.size());
        Assertions.assertTrue(path.getFirst().equals(cell));
    }

    @Test
    public void removeCellsAfter() {
        int i;
        List<ICell> cells = new LinkedList<>();
        ICell newCell = new Cell(0, 0);

        for (i = 0; i < 5; i++) {
            cells.add(new Cell(i, i));
        }

        cells.add(newCell);

        for (; i < 11; i++) {
            cells.add(new Cell(i, i));
        }

        path = new BasePath(cells);
        path.removeCellsAfter(newCell);
        Assertions.assertEquals(6, path.size());
        Assertions.assertEquals(newCell, path.getLast());
    }
}
