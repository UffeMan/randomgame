package se.microsft;

public class EnemyFactory {
    public Enemy createEnemy(String enemyType) {
        if (enemyType == null) return null;
        return switch (enemyType.toLowerCase()) {
            case "sloppy" -> new Sloppy();
            case "timely" -> new Timely();
            case "stalker" -> new Stalker();
            default -> null;
        };
    }
}
