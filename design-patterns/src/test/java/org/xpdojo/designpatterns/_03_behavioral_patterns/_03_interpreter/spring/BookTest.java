package org.xpdojo.designpatterns._03_behavioral_patterns._03_interpreter.spring;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    void sut_spring_expression_language_parser() {
        Book book = new Book("Spring in Action");

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("title");

        assertThat(expression.getValue(book)).isEqualTo("Spring in Action");
    }

}
