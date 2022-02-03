package com.advancewarsmobile.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.LinkedList;


public class AdvanceWarsMobile extends ApplicationAdapter {
	private final float WORLD_WIDTH = 96;
	private final float WORLD_HEIGHT = 128;
	private final String MAP_NAME = "map.tmx";

	// Graphics
	private TiledMap map;
	private Sprite infantry;

	// Units
	private Unit unit;

	private Unit.Stats infantryStats;

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

		// Stage setup
		stage = new Stage(viewport, renderer.getBatch());	// We use the renderer's batch to keep
		Gdx.input.setInputProcessor(stage);					// everything the stage renders the same
															// scale as the tilemap.
		// Designate unit sprite textures
		infantry = new Sprite(new Texture(Gdx.files.internal("infantry.png")));

		// Initialize default stats
		infantryStats = new Unit.Stats(1, 0, 3, 1, 1.5);

		// Make a unit
		unit = new Unit(0, infantry, "land", new Unit.Stats(infantryStats));
		unit.addListener(new MoveUnitListener(
				(TiledMapTileLayer) map.getLayers().get(0), new LinkedList<>()
		));
		unit.setPosition(64, 0);
		stage.addActor(unit);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);	// Screen must be refreshed before redrawing

		renderer.setView(camera);	// Sets the placement of the map under the camera
		renderer.render();

		stage.draw();	// Stage already uses the renderer's batch to draw to, so we don't need to
						// specify it here.
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
