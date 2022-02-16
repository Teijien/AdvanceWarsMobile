package com.advancewarsmobile.game;


public class BasicStats implements Stats {
    private int hp;
    private final int atk;
    private final int def;
    private final int mov;
    private final int range;
    private final double spd;


    public BasicStats(BasicStats stats) {
        this.hp = stats.hp;
        this.atk = stats.atk;
        this.def = stats.def;
        this.mov = stats.mov;
        this.range = stats.range;
        this.spd = stats.spd;
    }

    public BasicStats(int hp, int atk, int def, int mov, int range, double spd) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.mov = mov;
        this.range = range;
        this.spd = spd;
    }

    public int getHp() { return hp; }

    public int getAtk() { return atk; }

    public int getDef() { return def; }

    public void setHp(int hp) { this.hp = hp; }

    public int getMov() {
        return mov;
    }
}
