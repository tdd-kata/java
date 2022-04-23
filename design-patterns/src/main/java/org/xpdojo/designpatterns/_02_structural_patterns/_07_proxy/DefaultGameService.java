package org.xpdojo.designpatterns._02_structural_patterns._07_proxy;

public class DefaultGameService implements GameService {

    @Override
    public String startGame() {
        return "Game started";
    }
}
