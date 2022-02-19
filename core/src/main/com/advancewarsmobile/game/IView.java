package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface IView {
    void setView(OrthographicCamera camera);

    void render();

    void draw();

    Actor getActor(int x, int y);

    void addActor(Actor actor);

    void moveActor(ICell origin, ICell target);

    void deleteActor(int x, int y);

    void updateViewport(int width, int height);

    Camera getCamera();

    OrthogonalTiledMapRenderer getRenderer();
}
