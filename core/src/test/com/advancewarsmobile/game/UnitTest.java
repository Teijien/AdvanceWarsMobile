package com.advancewarsmobile.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class UnitTest {
    BasicStats stats = new BasicStats(100, 50, 10, 1, 1, 1);
    Unit atk;
    Unit def;

    @BeforeEach
    public void init() {
        atk = new BasicUnit(0, new BasicStats(stats));
        def = new BasicUnit(1, new BasicStats(stats));
    }

    @Test
    public void takeDmg() {
        def.takeDmg(50);
        Assertions.assertEquals(60, def.hp());
    }

    @Test
    public void takeNoDmg() {
        def.takeDmg(2);
        Assertions.assertEquals(100, def.hp());
    }

    @Test
    public void takeNegativeDmg() {
        def.takeDmg(-1);
        Assertions.assertEquals(100, def.hp());
    }

    @Test
    public void attackUnit() {
        atk.attackUnit(def);
        Assertions.assertEquals(60, def.hp());
        Assertions.assertEquals(80, atk.hp());
    }

    @Test
    public void killUnit() {
        Unit unit = new BasicUnit(0, new BasicStats(100, 110, 10, 1, 1, 1));
        unit.attackUnit(def);
        Assertions.assertTrue(def.hp() <= 0);
        Assertions.assertEquals(100, atk.hp());
    }
}
