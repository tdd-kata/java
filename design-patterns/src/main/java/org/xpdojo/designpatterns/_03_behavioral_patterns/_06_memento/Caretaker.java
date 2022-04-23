package org.xpdojo.designpatterns._03_behavioral_patterns._06_memento;

import java.util.Stack;

public class Caretaker {
    private Stack<GameSave> history;

    public Caretaker() {
        history = new Stack<>();
    }

    public void save(GameSave memento) {
        history.push(memento);
    }

    public GameSave undo() {
        return history.pop();
    }
}
