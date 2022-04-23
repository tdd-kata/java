package org.xpdojo.designpatterns._03_behavioral_patterns._02_command;

public class GameStartCommand implements Command {

    private Game game;

    public GameStartCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.start();
    }

    @Override
    public void undo() {
        new GameStopCommand(this.game).execute();
    }
}
