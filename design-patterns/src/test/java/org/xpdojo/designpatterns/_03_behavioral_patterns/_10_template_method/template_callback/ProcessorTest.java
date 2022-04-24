package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_callback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessorTest {

    @Test
    void sut_process() {
        Processor processor = new Processor();
        int plus = processor.process(new Plus());
        assertThat(plus).isEqualTo(7);

        int multiply = processor.process(new Multiply());
        assertThat(multiply).isEqualTo(12);

        int minus = processor.process((operand1, operand2) -> operand1 - operand2);
        assertThat(minus).isEqualTo(-1);
    }

}
