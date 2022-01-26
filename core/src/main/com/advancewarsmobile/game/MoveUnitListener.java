package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class MoveUnitListener extends InputListener {
    public MoveUnitListener() {
        super();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Actor actor = event.getTarget();

        actor.moveBy(x - (actor.getWidth() / 2), y - (actor.getHeight() / 2));
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        Actor actor = event.getTarget();

        actor.moveBy(x - (actor.getWidth() / 2), y - (actor.getHeight() / 2));
    }
}
