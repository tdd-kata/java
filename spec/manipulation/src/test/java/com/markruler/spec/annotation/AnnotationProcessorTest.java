package com.markruler.spec.annotation;

import com.markruler.spec.annotation.Hat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Custom Annotation Processor")
class AnnotationProcessorTest {

    @Test
    @DisplayName("컴파일 타임에 MagicHat 클래스를 생성한다")
    void sut_annotation_processor() {

        // gradle clean test -i
        /*
           > Task :compileJava
           ...
           Note: Processing Hat
         */
        // build/generated/sources/annotationProcessor/java/main/com/markruler/spec/annotation/MagicHat.java
        // build/classes/java/main/com/markruler/spec/annotation/MagicHat.class
        Hat hat = new MagicHat();

        assertThat(hat.pullout()).isEqualTo("Rabbit!");
    }
}
