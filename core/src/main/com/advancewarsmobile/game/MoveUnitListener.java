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

        // Guard statements
        if (!inBounds(cell)) { return; }
        if (cell.equals(path.getLast())) { return; }

        System.out.println("Moved to new tile!");

        // Remove all elements after current cell if it already exists in the path
        if (path.contains(cell)) {
            removeCellsAfter(cell);
            System.out.println();
            return;
        }

        checkPath(actor, cell);

        System.out.println();
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        Unit actor = (Unit) event.getTarget();
        Vector2 cell = path.getLast();

        // Place the unit in the last cell in the path
        actor.setPosition(
                cell.x * tiles.getTileWidth(),
                (tiles.getHeight() * tiles.getTileHeight())
                        - ((cell.y + 1) * tiles.getTileHeight())
        );
    }


    /* Bounds Checking */
    private boolean nextToTile(Vector2 origin, Vector2 target) {
        if (origin.equals(target)) { return false; }

        if (origin.x + 1 == target.x || origin.x - 1 == target.x) { return true; }
        return origin.y + 1 == target.y || origin.y - 1 == target.y;
    }

    private boolean inBounds(Vector2 cell) {
        if (!inXBounds(cell)) { return false; }
        return inYBounds(cell);
    }

    private boolean inXBounds(Vector2 cell) {
        if (cell.x < 0) { return false; }

        return !(cell.x > tiles.getWidth() - 1);
    }

    private boolean inYBounds(Vector2 cell) {
        if (cell.y < 0) { return false; }

        return !(cell.y > tiles.getHeight() - 1);
    }

    /* Cell */
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


    /* Path */

    // Checks if a cell can be added to the path, or if the path needs to be recalculated
    private void checkPath(Unit actor, Vector2 cell) {
        if (!(actor.getStats().getMov() > path.size() - 1)) {
            recalculatePath(cell, actor);
            return;
        }

        if (nextToTile(path.getLast(), cell)) {
            path.add(cell);
            System.out.println("Added tile to path");
        } else {
            recalculatePath(cell, actor);
        }
        /*
        if (actor.getStats().getMov() > path.size() - 1) {
            path.add(cell);
            System.out.println("Added tile to path");
        } else {
            recalculatePath(cell, actor);
        }

         */
    }

    private LinkedList<Vector2> getNewPath(LinkedList<Vector2> path, Vector2 targetCell,
                                           int unitMov) {
        LinkedList<Vector2> newPath;

        /*
        // Guard clause checks if we're still within the movement range
        if (path == null || moved > unitMov) {
            return null;
        }
        //*/

        newPath = new LinkedList<>(path);

        for (int i = 1; i <= unitMov; i++) {
            if (newPath.getLast().equals(targetCell)) { return newPath; }

            if (newPath.getLast().x > targetCell.x) {
                newPath.add(new Vector2(newPath.getLast().x - 1, newPath.getLast().y));
            } else if (newPath.getLast().x < targetCell.x) {
                newPath.add(new Vector2(newPath.getLast().x + 1, newPath.getLast().y));
            } else if (newPath.getLast().y > targetCell.y) {
                newPath.add(new Vector2(newPath.getLast().x, newPath.getLast().y - 1));
            } else if (newPath.getLast().y < targetCell.y) {
                newPath.add(new Vector2(newPath.getLast().x, newPath.getLast().y + 1));
            }
        }

        if (newPath.getLast().equals(targetCell)) { return newPath; }

        return null;

        // The below code does bounds checking, but we don't use inBounds()
        // because we need to return a different path for each outcome.
        // Below solution is recursive, but inefficient space-wise due to repeatedly making
        // new lists

        /*
        // Move on x-axis
        if (newPath.getLast().x > targetCell.x) {
            newPath.add(new Vector2(newPath.getLast().x - 1, newPath.getLast().y));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        }
        if (newPath.getLast().x < targetCell.x) {
            newPath.add(new Vector2(newPath.getLast().x + 1, newPath.getLast().y));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        }

        // Move on y-axis
        if (newPath.getLast().y > targetCell.y) {
            newPath.add(new Vector2(newPath.getLast().x, newPath.getLast().y - 1));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        }
        if (newPath.getLast().y < targetCell.y) {
            newPath.add(new Vector2(newPath.getLast().x, newPath.getLast().y + 1));
            newPath = getNewPath(newPath, targetCell, unitMov, moved + 1);
            return newPath;
        }

        return newPath;

        //*/
    }

    private void recalculatePath(Vector2 cell, Unit actor) {
        LinkedList<Vector2> temp = new LinkedList<>();
        temp.add(path.getFirst());
        temp = getNewPath(temp, cell, actor.getStats().getMov());

        replacePath(temp);
    }

    // Replaces the current path if the current cell is not a valid space to move to
    private void replacePath(LinkedList<Vector2> tempPath) {
        if (tempPath == null) {
            System.out.println("Not reachable");
        } else {
            path = tempPath;
            System.out.println("Recalculated path");
        }
    }

    private void removeCellsAfter(Vector2 cell) {
        while (path.indexOf(cell) < path.size() - 1) {
            path.removeLast();
        }
    }
}
