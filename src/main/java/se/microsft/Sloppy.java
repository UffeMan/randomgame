package se.microsft;

import java.util.Random;

/*
 *
 *   Fullständigt slumpmässigt
 *
 */
public class Sloppy implements Enemy {
    @Override
    public int getEnemyMove(String playerName) {
        Random rnd = new Random();
        int s = rnd.nextInt(1002);
        return s % 3;
    }
}
