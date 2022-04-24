package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_callback;

public class Plus implements Operator {
    @Override
    public int getResult(int operand1, int operand2) {
        return operand1 + operand2;
    }
}
