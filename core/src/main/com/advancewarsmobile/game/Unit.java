package com.advancewarsmobile.game;

public interface Unit {
    void attackUnit(Unit defender);

    void takeDmg(int dmg);

    Stats getStats();

    int mov();

    int hp();
}
