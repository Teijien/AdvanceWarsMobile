package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Unit extends Sprite {
    private final String type;

    public Unit(Texture texture, String type) {
        super(texture);
        this.type = type;
    }
}
