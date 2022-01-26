package com.advancewarsmobile.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class AdvanceWarsMobile extends ApplicationAdapter {
	private final float WORLD_WIDTH = 96;
	private final float WORLD_HEIGHT = 128;
	private final String MAP_NAME = "map.tmx";

	// Graphics
	private TiledMap map;
	private Sprite infantry;

	// Units
	private Infantry unit;

	// Camera
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ExtendViewport viewport;

	// Stage
	private Stage stage;


	@Override
	public void create () {
		// Map init
		map = new TmxMapLoader().load(MAP_NAME);

		// Camera and Renderer init
		renderer = new OrthogonalTiledMapRenderer(map);	// DO NOT SET "unitscale"! Messes with calcs
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

		stage = new Stage(viewport, renderer.getBatch());
		Gdx.input.setInputProcessor(stage);

		// Make a unit
		infantry = new Sprite(new Texture(Gdx.files.internal("infantry.png")));

		unit = new Infantry(infantry, "land");
		unit.addListener(new MoveUnitListener());
		unit.setPosition(64, 0);
		stage.addActor(unit);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);	// Screen must be refreshed before redrawing

		renderer.setView(camera);	// Sets the placement of the map under the camera
		renderer.render();

		stage.draw();
		//renderer.getBatch().begin();	// We use the batch from the renderer to draw the sprites
		//unit.draw(renderer.getBatch());	// using the same transformation matrix as the tile map
		//renderer.getBatch().end();
	}

	/* Viewport gets updated instead of camera, as all control
	 * of camera passes through viewport */
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);

		// Camera needs to be reset due Stage moving the camera
		// Also helps fix graphical issues when resizing window on PC
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		camera.update();
	}
	
	@Override
	public void dispose () {
		map.dispose();
		renderer.dispose();
		stage.dispose();
	}
}
