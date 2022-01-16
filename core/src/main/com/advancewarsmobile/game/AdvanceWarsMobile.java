package com.advancewarsmobile.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class AdvanceWarsMobile extends ApplicationAdapter {
	private static final int WORLD_WIDTH = 480;
	private static final int WORLD_HEIGHT = 640;
	private static final float TILE_SIZE = 1 / 80f;

	private SpriteBatch batch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ExtendViewport viewport;


	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new TmxMapLoader().load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLUE);

		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		renderer.setView(camera);
		renderer.render();

		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		map.dispose();
	}
}
