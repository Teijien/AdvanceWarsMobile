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


public class AdvanceWarsMobile extends ApplicationAdapter {
	private static final int WORLD_WIDTH = 480;
	private static final int WORLD_HEIGHT = 640;
	private static final String MAP_NAME = "map.tmx";

	private SpriteBatch batch;
	// Map
	private TiledMap map;
	private Map game;

	// Camera
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ExtendViewport viewport;


	@Override
	public void create () {
		//batch = new SpriteBatch();
		// Map init
		map = new TmxMapLoader().load(MAP_NAME);
		game = new Map(map);

		// Camera and Renderer init
		renderer = new OrthogonalTiledMapRenderer(map);	// DO NOT SET "unitscale"! Messes with calcs
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLUE);	// Screen must be refreshed before redrawing

		// Viewport gets updated instead of camera, as all control of camera passes through viewport
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		renderer.setView(camera);	// Sets the placement of the map under the camera
		renderer.render();

		//batch.begin();
		//batch.end();
	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		map.dispose();
	}
}
