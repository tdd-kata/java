package org.xpdojo.designpatterns._03_behavioral_patterns._02_command;

import java.util.Stack;

public class Console {

    private Stack<Command> commands = new Stack<>();

    public void press(Command command) {
        command.execute();
        commands.push(command);
    }

    public void undo() {
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
        }
    }
}
