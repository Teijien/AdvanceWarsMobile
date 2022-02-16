package com.advancewarsmobile.game;

public interface Path {
    void initializeNewPath(ICell cell);

    boolean removeCellsAfter(ICell cell);

    ICell getFirst();

    ICell getLast();

    int size();

    void add(ICell cell);

    void recalculatePath(ICell cell, int mov);
}
