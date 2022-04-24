package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_method;

public abstract class Processor {

    /**
     * Template Method
     *
     * @return 연산 결과
     */
    public final int process(int operand1, int operand2) {
        return getResult(operand1, operand2);
    }

    protected abstract int getResult(int operand1, int operand2);

}
