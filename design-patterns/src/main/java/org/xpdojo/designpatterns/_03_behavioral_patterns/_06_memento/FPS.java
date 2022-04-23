package org.xpdojo.designpatterns._03_behavioral_patterns._06_memento;

public class FPS implements Game {

    private int redTeamScore = 0;
    private int blueTeamScore = 0;
    private final Caretaker caretaker = new Caretaker();

    public int getRedTeamScore() {
        return redTeamScore;
    }

    public void setRedTeamScore(int redTeamScore) {
        this.redTeamScore = redTeamScore;
    }

    public int getBlueTeamScore() {
        return blueTeamScore;
    }

    public void setBlueTeamScore(int blueTeamScore) {
        this.blueTeamScore = blueTeamScore;
    }

    @Override
    public void save() {
        GameSave gameSave = new GameSave(this.blueTeamScore, this.redTeamScore);
        caretaker.save(gameSave);
    }

    @Override
    public void restore() {
        GameSave gameSave = caretaker.undo();
        this.blueTeamScore = gameSave.getBlueTeamScore();
        this.redTeamScore = gameSave.getRedTeamScore();
    }

}
