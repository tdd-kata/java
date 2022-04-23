package org.xpdojo.designpatterns._02_structural_patterns._07_proxy;

public class GameServiceProxy implements GameService {

    private GameService gameService;

    @Override
    public String startGame() {
        // pre-start game
        long before = System.currentTimeMillis();

        if (this.gameService == null) {
            this.gameService = new DefaultGameService();
        }

        String game = gameService.startGame();

        // post-start game
        System.out.println(System.currentTimeMillis() - before);

        return game;
    }
}
