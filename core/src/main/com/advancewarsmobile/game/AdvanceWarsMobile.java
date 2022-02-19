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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.LinkedList;


public class AdvanceWarsMobile extends ApplicationAdapter {
	private static final float WORLD_WIDTH = 96;
	private static final float WORLD_HEIGHT = 128;
	//private static final int TILE_SIZE = 16;
	private static final String MAP_NAME = "map.tmx";

	private View view;	// View


	@Override
	public void create () {
		// Map init
		// Graphics
		TiledMap map = new TmxMapLoader().load(MAP_NAME);
		// Controllers
		// Model
		Map gameMap = new GameMap(6, 8);

		// Camera and Renderer init
		OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);	// DO NOT SET "unitscale"! Messes with calcs

		OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

		ExtendViewport viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

		// Stage setup
		// Stage
		Stage stage = new Stage(viewport, renderer.getBatch());    // We use the renderer's batch to keep
		Gdx.input.setInputProcessor(stage);					// everything the stage renders the same
															// scale as the tilemap.
		// Designate basicUnit sprite textures
		Texture redInfantry = new Texture(Gdx.files.internal("infantry.png"));
		Texture blueInfantry = new Texture(Gdx.files.internal("infantryBlue.png"));

		// Initialize default stats
		BasicStats infantryStats = new BasicStats(100, 50, 10, 3, 1, 1.5);

		view = new View(renderer, viewport, stage, new Actor[6][8]);
		InputListener moveListener = new MapListener(
				gameMap,
				new BasePath(new LinkedList<>()),
				(TiledMapTileLayer) map.getLayers().get(0),
				view
		);
		Actor redActor = new UnitActor(new Sprite(redInfantry));
		Actor blueActor = new UnitActor(new Sprite(blueInfantry));
		redActor.addListener(moveListener);
		blueActor.addListener(moveListener);
		redActor.setPosition(48, 0);
		blueActor.setPosition(16, 112);
		gameMap.setUnit(
				new BasicUnit(0, new BasicStats(infantryStats)),
				3, 7
		);
		gameMap.setUnit(
				new BasicUnit(1, new BasicStats(infantryStats)),
				1, 0
		);

		view.addActor(redActor);
		view.addActor(blueActor);

		// Listeners
		//mapListener = new MapListener(
		//		(TiledMapTileLayer) map.getLayers().get(0), new LinkedList<>());

		// Make a basicUnit
		//redTeam = makeUnits(5, 0, "land", infantry, infantryStats);
		//blueTeam = makeUnits(5, 1, "land", infantry, infantryStats);

		//addListeners(redTeam, mapListener);
		//addListeners(blueTeam, mapListener);

		//setRedUnitPositions(redTeam, TILE_SIZE);
		//setBlueUnitPositions(blueTeam, TILE_SIZE);
		//basicUnit = new Unit(0, infantry, "land", infantryStats);
		//basicUnit.setPosition(64, 0);
		//basicUnit.addListener(moveUnitListener);

		//stage.addActor(basicUnit);
		//addActorsToStage(redTeam);
		//addActorsToStage(blueTeam);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);	// Screen must be refreshed before redrawing

		view.setView((OrthographicCamera) view.getCamera());	// Sets the placement of the map under the camera
		view.render();

		view.draw();	// Stage already uses the renderer's batch to draw to, so we don't need to
						// specify it here.
	}

	/* Viewport gets updated instead of camera, as all control
	 * of camera passes through viewport */
	@Override
	public void resize(int width, int height) {
		view.updateViewport(width, height);

		// Camera needs to be reset due Stage moving the camera
		// Also helps fix graphical issues when resizing window on PC
		view.getCamera().position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		view.getCamera().update();
	}
	
	@Override
	public void dispose () {
		view.dispose();
	}
}