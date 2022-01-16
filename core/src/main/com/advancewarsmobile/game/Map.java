package com.advancewarsmobile.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;


/* Definition of Map class */
public class Map {
    private static final String MAP_TILES = "plainsTile-border";
    private static final int PLAINS_ID = 0;

    private TiledMap map;
    private TiledMapTileSet tileset;


    public Map(TiledMap map) {
        this.map = map;
        tileset = map.getTileSets().getTileSet(MAP_TILES);
    }

    private Map () {}

    private TiledMap getTiledMap() {
        return map;
    }

    private TiledMapTileSet getTileSet() {
        return tileset;
    }
}
