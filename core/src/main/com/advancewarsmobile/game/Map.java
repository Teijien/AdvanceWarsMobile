package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

/* Definition of Map class
*
*  Contains the tilemaps as an array of ints representing
*  tile types and methods to display them. */
public class Map {
    public enum Tile {
        PLAINS
    }
    private Texture plainTile;
    private Texture cityTile;
    private Tile[][] tilemap;
    private TiledMap map;

    private Map() {}

    public Map(Texture plainTile, Texture cityTile, TiledMap map, int w, int h) {
        this.plainTile = plainTile;
        this.cityTile = cityTile;
        this.map = map;
        tilemap = fillWithPlains(w, h);
    }

    public Tile[][] getTilemap() {
        return tilemap;
    }

    /* Method fillWithPlains(int, int)
    *  This method is used to fill the map with
    *  Plains tiles */
    private Tile[][] fillWithPlains(int w, int h) {
        Tile[][] t;

        if (w == 0 || h == 0) {
            System.out.println("Map size cannot be 0");
            return null;
        }

        if (w > h) {
            int temp = w;
            w = h;
            h = temp;
        }

        try {
            t = new Tile[w][h];
        } catch(NegativeArraySizeException e) {
            System.out.println("Array size should not be negative");
            return null;
        }

        // Set all tiles in map to Plains
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = Tile.PLAINS;
            }
        }

        return t;
    }
}
