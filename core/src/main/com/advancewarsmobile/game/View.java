package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public record View(TiledMapRenderer renderer, Viewport viewport, Stage stage) implements IView {

    public void setView(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public void render() {
        renderer.render();
    }

    public void draw() {
        stage.draw();
    }

    public void addActor(Actor actor) {
        stage.addActor(actor);
    }

    public void updateViewport(int width, int height) {
        viewport.update(width, height);
    }
}
