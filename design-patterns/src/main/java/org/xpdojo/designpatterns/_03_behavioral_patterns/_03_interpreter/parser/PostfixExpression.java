package org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.parser;

import java.util.Map;

public interface PostfixExpression {

    int interpret(Map<Character, Integer> context);

}
