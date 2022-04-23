package org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.parser;

import lombok.ToString;

import java.util.Map;

@ToString
public class VariableExpression implements PostfixExpression {

    private final Character character;

    public VariableExpression(Character character) {
        this.character = character;
    }

    @Override
    public int interpret(Map<Character, Integer> context) {
        return context.get(this.character);
    }
}
