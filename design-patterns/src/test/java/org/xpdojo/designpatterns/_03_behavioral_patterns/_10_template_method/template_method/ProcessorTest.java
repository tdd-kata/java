package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessorTest {

    @Test
    void sut_template_method() {
        Processor plus = new Plus();
        int result1 = plus.process(3, 4);
        assertThat(result1).isEqualTo(7);

        Multiply multiply = new Multiply();
        int result2 = multiply.process(3, 4);
        assertThat(result2).isEqualTo(12);
    }

}
