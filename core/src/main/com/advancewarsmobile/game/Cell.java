package com.advancewarsmobile.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Cell implements ICell{
    private int x;
    private int y;

    public Cell(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("X cannot be negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("Y cannot be negative");
        }

        this.x = x;
        this.y = y;
    }

    public Cell(InputEvent event, TiledMapTileLayer tiles) {
        // Need to unproject mouse coordinates before polling for tile
        Vector3 cursorPos = event.getTarget()
                .getStage()
                .getCamera()
                .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        this.x = (int) cursorPos.x / tiles.getTileWidth();
        this.y = (tiles.getHeight() * tiles.getTileHeight()
                - (int) cursorPos.y) / tiles.getTileHeight();
    }

    public void setCell(ICell cell) {
        x = cell.x();
        y = cell.y();
    }

    public boolean nextToCell(ICell target) {
        if (this.equals(target)) { return false; }

        if (this.x() + 1 == target.x() || this.x() - 1 == target.x()) { return true; }
        return this.y() + 1 == target.y() || this.y() - 1 == target.y();
    }

    public boolean inBounds(TiledMapTileLayer tiles) {
        if (!inXBounds(tiles)) { return false; }
        return inYBounds(tiles);
    }

    private boolean inXBounds(TiledMapTileLayer tiles) {
        if (x < 0) { return false; }
        return !(x > tiles.getWidth() - 1);
    }

    private boolean inYBounds(TiledMapTileLayer tiles) {
        if (y < 0) { return false; }
        return !(y > tiles.getHeight() - 1);
    }

    public boolean equals(ICell cell) {
        return this.x == cell.x() && this.y == cell.y();
    }

    public int x() {
        return x;
    }

    public int y() { return y; }
}
