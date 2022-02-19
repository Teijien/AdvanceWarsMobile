package com.advancewarsmobile.game;

public record BasicUnit(int team, Stats stats) implements Unit {

    public void attackUnit(Unit defender) {
        defender.takeDmg(stats.getAtk() * hp() / 100);
        if (defender.getStats().getHp() > 0) {
            takeDmg(defender.getStats().getAtk() * defender.hp() / 100);
        }
    }

    public void takeDmg(int atk) {
        int dmg = hp() - (atk - stats.getDef());
        if (stats.getDef() <= atk) {
            stats.setHp(dmg);
        }
        System.out.println(team + " unit took " + (atk - stats.getDef()) + " damage! HP = " + hp());
    }

    public Stats getStats() {
        return stats;
    }

    public int mov() {
        return getStats().getMov();
    }

    public int hp() {
        return getStats().getHp();
    }
}
