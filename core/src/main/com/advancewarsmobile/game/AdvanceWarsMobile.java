package com.advancewarsmobile.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;


public class AdvanceWarsMobile extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture plainsTile;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		plainsTile = new Texture("plainsTile.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(plainsTile, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		plainsTile.dispose();
	}
}
