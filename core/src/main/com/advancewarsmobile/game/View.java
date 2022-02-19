package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class View implements IView, Disposable, CellValidator {
    OrthogonalTiledMapRenderer renderer;
    Viewport viewport;
    Stage stage;
    Actor[][] actors;

    public View(OrthogonalTiledMapRenderer renderer, Viewport viewport, Stage stage, Actor[][] actors) {
        this.renderer = renderer;
        this.viewport = viewport;
        this.stage = stage;
        this.actors = actors;
    }

    public void setView(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public void render() {
        renderer.render();
    }

    public void draw() {
        stage.draw();
    }

    public Actor getActor(int x, int y) {
        return actors[x][y];
    }

    public void addActor(Actor actor) {
        TiledMapTileLayer layer = (TiledMapTileLayer) renderer.getMap().getLayers().get(0);
        int screenHeight = layer.getHeight() * (layer.getTileHeight() - 1);
        int x = (int) actor.getX() / 16;
        int y = (screenHeight - (int) actor.getY()) / 16;

        stage.addActor(actor);
        actors[x][y] = actor;
    }

    public void moveActor(ICell origin, ICell target) {
        validateCells(origin, target);

        actors[target.x()][target.y()] = actors[origin.x()][origin.y()];
        actors[origin.x()][origin.y()] = null;
    }

    public void deleteActor(int x, int y) {
        actors[x][y].remove();
        actors[x][y] = null;
    }

    public void updateViewport(int width, int height) {
        viewport.update(width, height);
    }

    public Camera getCamera() {
        return viewport.getCamera();
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose() {
        renderer.getMap().dispose();
        renderer.dispose();
        stage.dispose();
    }

    public void validateCells(ICell origin, ICell target) {
        validateOrigin(origin);
        validateTarget(target);
    }

    private void validateOrigin(ICell cell) {
        if (actors[cell.x()][cell.y()] == null) {
            throw new IllegalArgumentException(
                    "No Actor at origin cell (" + cell.x() + "," + cell.y() + ")"
            );
        }
    }

    private void validateTarget(ICell cell) {
        if (actors[cell.x()][cell.y()] != null) {
            throw new IllegalStateException(
                    "Cannot move Actor to filled cell (" + cell.x() + "," + cell.y() + ")"
            );
        }
    }
}
