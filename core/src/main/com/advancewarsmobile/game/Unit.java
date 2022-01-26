package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;


public abstract class Unit extends Actor {
    private Sprite sprite;
    private final String type;

    public Unit(Sprite sprite, String type) {
        this.sprite = sprite;
        this.type = type;
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    public boolean addListener(EventListener listener) {
        return super.addListener(listener);
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        sprite.setPosition(this.getX(), this.getY());
    }
}
