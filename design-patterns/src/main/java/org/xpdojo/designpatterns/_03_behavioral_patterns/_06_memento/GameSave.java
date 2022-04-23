package org.xpdojo.designpatterns._03_behavioral_patterns._06_memento;

/**
 * Memento
 */
public final class GameSave {

    private final int blueTeamScore;
    private final int redTeamScore;

    public GameSave(int blueTeamScore, int redTeamScore) {
        this.blueTeamScore = blueTeamScore;
        this.redTeamScore = redTeamScore;
    }

    public int getBlueTeamScore() {
        return blueTeamScore;
    }

    public int getRedTeamScore() {
        return redTeamScore;
    }

    public GameSave restore() {
        return this;
    }

    @Override
    public String toString() {
        return "GameSave{" +
                "blueTeamScore=" + blueTeamScore +
                ", redTeamScore=" + redTeamScore +
                '}';
    }
}
