package com.markruler.spec.bytecode;

import com.markruler.spec.bytecode.AnyObject;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

// https://github.com/raphw/byte-buddy/blob/byte-buddy-1.12.6/byte-buddy-dep/src/test/java/net/bytebuddy/ByteBuddyTutorialExamplesTest.java
@DisplayName("Bytecode Manipulation")
class BytecodeManipulationTest {

    @Test
    @DisplayName("Object 바이트 코드 조작")
    void sut_Object_should_be_manipulated() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        final String methodName = "toString";
        final String fixedValue = "Hello World!";

        DynamicType.Unloaded<Object> unloadedSubclass = new ByteBuddy()
                .subclass(Object.class)
                .method(named(methodName))
                .intercept(FixedValue.value(fixedValue))
                .make();

        Class<?> reload = unloadedSubclass
                .load(this.getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Object newInstance = reload.getConstructor().newInstance();
        assertThat(newInstance).hasToString(fixedValue);
    }

    @Test
    @DisplayName("Hat 바이트 코드 조작")
    void sut_Hat_should_be_manipulated() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        final String methodName = "pullout";
        final String fixedValue = "Fix!";

        DynamicType.Unloaded<AnyObject> redefinedClass = new ByteBuddy()
                .redefine(AnyObject.class)
                .method(named(methodName))
                .intercept(FixedValue.value(fixedValue))
                .make();
        Class<?> dynamicType = redefinedClass
                .load(ClassLoadingStrategy.BOOTSTRAP_LOADER, ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Method pullout = dynamicType.getDeclaredMethod(methodName);
        Object newInstance = dynamicType.getConstructor().newInstance();
        assertThat(pullout.invoke(newInstance)).isEqualTo(fixedValue);
    }
}
