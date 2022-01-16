import com.advancewarsmobile.game.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class MapTest {
    private Texture plainStub;
    private Texture cityStub;
    private TiledMap mapStub;

    /*
    @Test
    public void mapConstructorTest_default() {
        setStubs_null();
        Map map = new Map();

        assertNull(map.getTilemap());
    }
    */

    @Test
    public void mapConstructorTest_input() {
        setStubs_null();
        Map map = new Map(plainStub, cityStub, mapStub, 6, 8);

        checkTiles(map, Map.Tile.PLAINS);
    }

    @Test
    public void mapConstructorTest_reverse() {
        setStubs_null();
        Map map = new Map(plainStub, cityStub, mapStub, 8, 6);

        checkTiles(map, Map.Tile.PLAINS);
    }

    @Test
    public void mapConstructorTest_negative() {
        setStubs_null();
        Map map = new Map(plainStub, cityStub, mapStub, -1, -1);
        assertNull(map.getTilemap());
    }

    @Test
    public void mapConstructorTest_empty() {
        setStubs_null();
        Map m1 = new Map(plainStub, cityStub, mapStub, 0, 1);
        Map m2 = new Map(plainStub, cityStub, mapStub, 1, 0);

        assertNull(m1.getTilemap());
        assertNull(m2.getTilemap());
    }

    private void checkTiles(Map map, Map.Tile tile) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertSame(map.getTilemap()[i][j], tile);
            }
        }
    }

    private void setStubs_null() {
        plainStub = null;
        cityStub = null;
        mapStub = null;
    }
}
