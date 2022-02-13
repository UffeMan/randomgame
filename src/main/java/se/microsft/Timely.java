package se.microsft;

import java.util.Date;

/*
 *
 *   sekund påverkar mitt val
 *
 */
public class Timely implements Enemy {
    @Override
    public int getEnemyMove(String playerName) {
        Date date = new Date();
        long second = date.getTime() / 1000;
        return (int) second % 3;
    }
}
