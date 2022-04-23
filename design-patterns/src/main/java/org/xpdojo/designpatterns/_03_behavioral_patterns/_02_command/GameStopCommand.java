package org.xpdojo.designpatterns._03_behavioral_patterns._02_command;

public class GameStopCommand implements Command {

    private Game game;

    public GameStopCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.stop();
    }

    @Override
    public void undo() {
        new GameStartCommand(this.game).execute();
    }
}
