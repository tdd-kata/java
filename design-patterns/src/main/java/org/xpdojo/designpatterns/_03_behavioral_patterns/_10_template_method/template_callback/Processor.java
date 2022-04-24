package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_callback;

public class Processor {

    /**
     * Template Callback
     * 
     * @param operator 연산자
     * @return 연산 결과
     */
    public final int process(Operator operator) {
        int externalState1 = 3;
        int externalState2 = Integer.parseInt("4");
        return operator.getResult(externalState1, externalState2);
    }

}
