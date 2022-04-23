package org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.parser;

import lombok.ToString;

import java.util.Map;

@ToString
public class MinusExpression implements PostfixExpression {

    private final PostfixExpression left;
    private final PostfixExpression right;

    public MinusExpression(PostfixExpression left, PostfixExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Map<Character, Integer> context) {
        return left.interpret(context) - right.interpret(context);
    }
}
