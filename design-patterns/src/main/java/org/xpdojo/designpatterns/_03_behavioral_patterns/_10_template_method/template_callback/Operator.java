package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_callback;

@FunctionalInterface
public interface Operator {
    int getResult(int operand1, int operand2);
}
