package org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.parser;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.parser.PostfixExpression;
import org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.parser.PostfixParser;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PostfixParserTest {

    @Test
    void sut_postfix_parser() {
        PostfixExpression expression = PostfixParser.parse("xyz+-a+");
        int result = expression.interpret(Map.of('x', 1, 'y', 2, 'z', 3, 'a', 5));
        assertThat(result).isEqualTo(1 - (2 + 3) + 5);
    }

}
