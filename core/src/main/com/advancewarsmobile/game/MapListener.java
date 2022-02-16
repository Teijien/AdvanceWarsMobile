package com.advancewarsmobile.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MapListener extends InputListener {
    private final Map map;
    private final Path path;
    private final TiledMapTileLayer tiles;

    public MapListener(Map map, Path path, TiledMapTileLayer tiles) {
        super();
        this.map = map;
        this.path = path;
        this.tiles = tiles;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer != 0) {
            return false;
        }

        event.getTarget()
                .moveBy(x - (event.getTarget().getWidth() / 2),
                        y - (event.getTarget().getHeight() / 2));

        path.initializeNewPath(new Cell(event, tiles));

        return true;
    }

    // Note that x and y are deltas, not positional values!
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        Cell cell = new Cell(event, tiles);

        event.getTarget()
                .moveBy(x - (event.getTarget().getWidth() / 2),
                        y - (event.getTarget().getHeight() / 2));

        if (!validateCell(cell, tiles)) { return; }

        if (path.removeCellsAfter(cell)) { return; }


        if (path.getLast().nextToCell(cell)
                && map.getUnit(path.getFirst().x(), path.getFirst().y()).mov() >= path.size()) {
            path.add(cell);
            System.out.println("Added tile to path");
        } else {
            path.recalculatePath(cell, map.getUnit(path.getFirst().x(), path.getFirst().y()).mov());
        }

        System.out.println();
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (!path.getFirst().equals(path.getLast())) {
            map.moveUnit(path.getFirst(), path.getLast());
        }
        event.getTarget()
                .setPosition(
                        path.getLast().x() * tiles.getTileWidth(),
                        tiles.getHeight() * tiles.getTileHeight()
                                - (path.getLast().y() + 1) * tiles.getTileHeight()
                );
    }


    private boolean validateCell(Cell cell, TiledMapTileLayer tiles) {
        if (!cell.inBounds(tiles)) { return false; }
        if (cell.equals(path.getLast())) { return false; }

        System.out.println("Moved to new tile!");

        return true;
    }
}