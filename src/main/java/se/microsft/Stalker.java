package se.microsft;

import java.util.stream.IntStream;

/*
 *
 *   vokaler i motståndaren påverkar mitt val
 *
 */
public class Stalker implements Enemy {
    @Override
    public int getEnemyMove(@org.jetbrains.annotations.NotNull String playerName) {
        int antalVokaler = (int) IntStream.range(0, playerName.length()).filter(i -> playerName.charAt(i) == 'a' ||
                playerName.charAt(i) == 'e' ||
                playerName.charAt(i) == 'i' ||
                playerName.charAt(i) == 'o' ||
                playerName.charAt(i) == 'u' ||
                playerName.charAt(i) == 'y' ||
                playerName.charAt(i) == 'å' ||
                playerName.charAt(i) == 'ä' ||
                playerName.charAt(i) == 'ö').count();
        return antalVokaler % 3;
    }
}
