package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;


public class Unit extends Actor {
    public static class Stats {
        private int atk;
        private int def;
        private int mov;
        private int range;
        private double spd;


        public Stats(Stats stats) {
            this.atk = stats.atk;
            this.def = stats.def;
            this.mov = stats.mov;
            this.range = stats.range;
            this.spd = stats.spd;
        }
        public Stats(int atk, int def, int mov, int range, double spd) {
            this.atk = atk;
            this.def = def;
            this.mov = mov;
            this.range = range;
            this.spd = spd;
        }
    }

    private int team;
    private Sprite sprite;
    private Stats stats;
    private final String type;


    public Unit(int team, Sprite sprite, String type, Stats stats) {
        this.team = team;
        this.sprite = sprite;
        this.type = type;
        this.stats = stats;
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
