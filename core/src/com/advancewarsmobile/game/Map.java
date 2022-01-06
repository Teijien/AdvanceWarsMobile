package com.advancewarsmobile.game;

import com.badlogic.gdx.graphics.Texture;

/* Definition of Map class
*
*  Contains the tilemaps as an array of ints representing
*  tile types and methods to display them. */
public class Map {
    private Texture plainTile;
    private Texture cityTile;
    private Tile[][] tilemap;
    private enum Tile {
        PLAINS,
        CITY
    }


    public Map() {
        tilemap = createMap();
    }

    public Map(int w, int h) {
        tilemap = createMap(w, h);
    }


    /* Method createMap()
    *  Default constructor initializes tilemap as
    *  an 8x6 map filled with Plains tiles. */
    public Tile[][] createMap() {
        Tile[][] t = fillWithPlains(6, 8);
        return t;
    }

    /* Method createMap(int, int)
    *  Specifies the width and height of the tilemap
    *  to be created. */
    public Tile[][] createMap(int w, int h) {
        // Swap width and height if w > h
        if (w > h) {
            int temp = w;
            w = h;
            h = temp;
        }

        return fillWithPlains(w, h);
    }


    /* Method fillWithPlains(int, int)
    *  This method is used to fill the map with
    *  Plains tiles */
    private Tile[][] fillWithPlains(int w, int h) {
        Tile[][] t = new Tile[w][h];

        // Set all tiles in map to Plains
        for (int i = 0; i < t[0].length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = Tile.PLAINS;
            }
        }

        return t;
    }
}
