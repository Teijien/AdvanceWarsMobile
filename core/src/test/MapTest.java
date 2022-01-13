import com.advancewarsmobile.game.Map;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class MapTest {

    @Test
    public void mapConstructorTest_default() {
        Map map = new Map();

        checkTiles(map);
    }

    @Test
    public void mapConstructorTest_input() {
        Map map = new Map(6, 8);

        checkTiles(map);
    }

    @Test
    public void mapConstructorTest_reverse() {
        Map map = new Map(8, 6);

        checkTiles(map);
    }

    @Test
    public void mapConstructorTest_negative() {
        Map map = new Map(-1, -1);
        assertNull(map.getTilemap());
    }

    @Test
    public void mapConstructorTest_empty() {
        Map m1 = new Map(0, 1);
        Map m2 = new Map(1, 0);

        assertNull(m1.getTilemap());
        assertNull(m2.getTilemap());
    }

    private void checkTiles(Map map) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertSame(map.getTilemap()[i][j], Map.Tile.PLAINS);
            }
        }
    }
}
