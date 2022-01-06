package com.markruler.spec.reflection;

import com.markruler.spec.reflection.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Reflection")
class ReflectionTest {

    @Test
    @DisplayName("Class Type")
    void sut_class_type() {

        final Class<Book> bookClass = Book.class;

        assertThat(bookClass.getName()).isEqualTo("com.markruler.spec.reflection.Book");
        assertThat(bookClass.getSimpleName()).isEqualTo("Book");

        assertThat(bookClass.getFields()).hasSize(1);
    }

    @Test
    @DisplayName("Class Type - public이 아닌 필드에 접근하기")
    void sut_class_type_modifiers() throws NoSuchFieldException, IllegalAccessException {

        final Class<Book> bookClass = Book.class;

        assertThat(bookClass.getDeclaredFields()).hasSize(6);
        assertThat(bookClass.getDeclaredField("privateStatic").toGenericString()).isEqualTo("private static java.lang.String com.markruler.spec.reflection.Book.privateStatic");

        final Field privateString = bookClass.getDeclaredField("privateStatic");
        final int privateStringModifiers = privateString.getModifiers();
        assertThat(Modifier.isPrivate(privateStringModifiers)).isTrue();
        assertThat(Modifier.isStatic(privateStringModifiers)).isTrue();
        assertThat(Modifier.isFinal(privateStringModifiers)).isFalse();

        privateString.setAccessible(true); // private이어도 접근할 수 있도록 설정
        assertThat(privateString.get(null)).isEqualTo("privateStatic value");
    }

    @Test
    @DisplayName("Class Type - public 필드에 접근하기")
    void sut_class_type_public_modifier() throws NoSuchFieldException, IllegalAccessException {

        final Class<Book> bookClass = Book.class;

        final Field publicString = bookClass.getDeclaredField("publicString");
        final int publicStringModifiers = publicString.getModifiers();
        assertThat(Modifier.isPrivate(publicStringModifiers)).isFalse();
        assertThat(Modifier.isStatic(publicStringModifiers)).isFalse();
        assertThat(Modifier.isFinal(publicStringModifiers)).isFalse();

        // static field가 아니기 때문에 Instance를 인자로 넘겨준다.
        final Book book = new Book();
        assertThat(publicString.get(book)).isEqualTo("public value");
    }

    @Test
    @DisplayName("Class Type - public이 아닌 메서드에 접근하기")
    void sut_class_type_methods() throws NoSuchMethodException {

        final Class<Book> bookClass = Book.class;

        final Method privateVoid = bookClass.getDeclaredMethod("privateVoid");
        assertThat(privateVoid.toGenericString()).isEqualTo("private void com.markruler.spec.reflection.Book.privateVoid()");
    }

    @Test
    @DisplayName("Class name")
    void sut_class_name() throws ClassNotFoundException {

        // when
        final Class<Book> bookClass = Book.class;
        final String className = bookClass.getName();

        // given
        final Class<?> bookClassByName = Class.forName(className);

        // then
        assertThat(bookClassByName.getName()).isEqualTo("com.markruler.spec.reflection.Book");
        assertThat(bookClassByName.getSimpleName()).isEqualTo("Book");
    }

    @Test
    @DisplayName("Class Instance")
    void sut_class_instance() {

        final Book book = new Book();
        final Class<? extends Book> bookClass = book.getClass();

        assertThat(bookClass.getName()).isEqualTo("com.markruler.spec.reflection.Book");
        assertThat(bookClass.getSimpleName()).isEqualTo("Book");
    }
}
