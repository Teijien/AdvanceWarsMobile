package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface IView {
    void setView(OrthographicCamera camera);

    void render();

    void draw();

    void addActor(Actor actor);

    void updateViewport(int width, int height);
}
