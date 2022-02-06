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
	private final int TILE_SIZE = 16;
	private final String MAP_NAME = "map.tmx";

	// Graphics
	private TiledMap map;
	private Texture infantry;

	// Units
	private Unit[] redTeam;
	private Unit[] blueTeam;
	//private Unit unit;

	private Unit.Stats infantryStats;

	// Camera
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ExtendViewport viewport;

	// Stage
	private Stage stage;

	// Controllers
	private MoveUnitListener moveUnitListener;


	@Override
	public void create () {
		// Map init
		map = new TmxMapLoader().load(MAP_NAME);

		// Camera and Renderer init
		renderer = new OrthogonalTiledMapRenderer(map);	// DO NOT SET "unitscale"! Messes with calcs

		camera = cameraInit(WORLD_WIDTH, WORLD_HEIGHT, false);

		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

		// Stage setup
		stage = new Stage(viewport, renderer.getBatch());	// We use the renderer's batch to keep
		Gdx.input.setInputProcessor(stage);					// everything the stage renders the same
															// scale as the tilemap.
		// Designate unit sprite textures
		infantry = new Texture(Gdx.files.internal("infantry.png"));

		// Initialize default stats
		infantryStats = new Unit.Stats(1, 0, 3, 1, 1.5);

		// Listeners
		moveUnitListener = new MoveUnitListener(
				(TiledMapTileLayer) map.getLayers().get(0), new LinkedList<>());

		// Make a unit
		redTeam = makeUnits(5, 0, "land", infantry, infantryStats);
		blueTeam = makeUnits(5, 1, "land", infantry, infantryStats);

		addListeners(redTeam, moveUnitListener);
		addListeners(blueTeam, moveUnitListener);

		setRedUnitPositions(redTeam, TILE_SIZE);
		setBlueUnitPositions(blueTeam, TILE_SIZE);
		//unit = new Unit(0, infantry, "land", infantryStats);
		//unit.setPosition(64, 0);
		//unit.addListener(moveUnitListener);

		//stage.addActor(unit);
		addActorsToStage(redTeam);
		addActorsToStage(blueTeam);
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

	private OrthographicCamera cameraInit(float width, float height, boolean yDown) {
		OrthographicCamera newCamera = new OrthographicCamera(width, height);
		newCamera.setToOrtho(yDown, width, height);

		return newCamera;
	}

	private Unit[] makeUnits(int size, int team, String type, Texture sprite, Unit.Stats stats) {
		Unit[] units = new Unit[size];

		for (int i = 0; i < size; i++) {
			units[i] = new Unit(team, new Sprite(sprite), type, stats);
		}

		return units;
	}

	private void addListeners(Unit[] units, MoveUnitListener moveUnitListener) {
		for (Unit unit : units) {
			unit.addListener(moveUnitListener);
		}
	}

	private void setRedUnitPositions(Unit[] units, int tileSize) {
		for (int i = 0; i < units.length; i++) {
			units[i].setPosition(48 + i / 2 * tileSize, (i + 1) % 2 * tileSize);
		}
	}

	private void setBlueUnitPositions(Unit[] units, int tileSize) {
		for (int i = 0; i < units.length; i++) {
			units[i].setPosition((i + 1) / 2 * tileSize, (i % 2 + 6) * tileSize);
		}
	}

	private void addActorsToStage(Unit[] units) {
		for (Unit unit : units) {
			stage.addActor(unit);
		}
	}
}