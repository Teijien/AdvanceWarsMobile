package com.advancewarsmobile.game;

import com.badlogic.gdx.scenes.scene2d.EventListener;

public interface IUnitActor {
    boolean addListener(EventListener listener);

    void setPosition(float x, float y);

    void moveBy(float x, float y);
}
