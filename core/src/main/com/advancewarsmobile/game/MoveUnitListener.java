package com.advancewarsmobile.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.LinkedList;


public class MoveUnitListener extends InputListener {
    private TiledMapTileLayer tiles;
    private LinkedList<Vector2> path;
    private int screenHeight;


    public MoveUnitListener(TiledMapTileLayer tiles, LinkedList<Vector2> path) {
        super();
        this.tiles = tiles;
        this.path = path;
        this.screenHeight = tiles.getHeight() * tiles.getTileHeight();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Actor actor = event.getTarget();
        Vector2 cell = getCurrentCell(event);

        if (tiles.getCell((int) cell.x, (int) cell.y) != null) {
            path.clear();
            path.add(cell);
        }

        actor.moveBy(x - (actor.getWidth() / 2), y - (actor.getHeight() / 2));

        return true;
    }

    // Note that x and y are deltas, not positional values!
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        Unit actor = (Unit) event.getTarget();
        Vector2 cell = getCurrentCell(event);

        actor.moveBy(x - (actor.getWidth() / 2), y - (actor.getHeight() / 2));

        if (!cell.equals(path.getLast())) {
            System.out.println("Moved to new tile!");
            if (!path.contains(cell)) {
                if (actor.getStats().getMov() > path.size() - 1) {
                    path.add(cell);
                    System.out.println("Added tile to path");
                } else {
                    LinkedList<Vector2> temp = new LinkedList<>();
                    temp.add(path.getFirst());
                    temp = getNewPath(temp, cell, actor.getStats().getMov(), 1);

                    if (temp == null) {
                        System.out.println("Not reachable");
                    } else {
                        path = temp;
                        System.out.println("Recalculated path");
                    }
                }
            } else {
                // Remove all elements after currentCell
                while (path.indexOf(cell) < path.size() - 1) {
                    path.removeLast();
                }
            }
            System.out.println();
        }
    }

    private Vector2 getCurrentCell(InputEvent event) {
        Actor actor = event.getTarget();
        Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        int cellX, cellY;

        // Need to unproject mouse coordinates before polling for tile
        cursorPos = actor.getStage().getCamera().unproject(cursorPos);

        cellX = (int) cursorPos.x / tiles.getTileWidth();
        cellY = (screenHeight - (int) cursorPos.y) / tiles.getTileHeight();

        return new Vector2(cellX, cellY);
    }

    private LinkedList<Vector2> getNewPath(LinkedList<Vector2> path, Vector2 targetCell,
                                           int unitMov, int moved) {
        LinkedList<Vector2> newPath;

        // Guard clause checks if we're still within the movement range
        if (path == null || moved > unitMov) {
            return null;
        }

        newPath = new LinkedList<>(path);

        // Move on x-axis
        if (newPath.getLast().x > targetCell.x) {
            newPath.add(new Vector2(newPath.getLast().x - 1, newPath.getLast().y));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        } else if (newPath.getLast().x < targetCell.x) {
            newPath.add(new Vector2(newPath.getLast().x + 1, newPath.getLast().y));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        }


        // Move on y-axis
        if (newPath.getLast().y > targetCell.y) {
            newPath.add(new Vector2(newPath.getLast().x, newPath.getLast().y - 1));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        } else if (newPath.getLast().y < targetCell.y) {
            newPath.add(new Vector2(newPath.getLast().x, newPath.getLast().y + 1));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        }

        return newPath;
    }
}
