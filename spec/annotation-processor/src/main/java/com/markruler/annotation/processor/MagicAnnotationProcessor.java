package com.markruler.annotation.processor;

import com.google.auto.service.AutoService;
import com.markruler.annotation.Magic;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class MagicAnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);
        for (Element element : elements) {
            final Name elementSimpleName = element.getSimpleName();
            if (element.getKind() != ElementKind.INTERFACE) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Magic Annotation cannot be used on " + elementSimpleName);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + elementSimpleName);
            }

            final TypeElement typeElement = (TypeElement) element;
            final ClassName className = ClassName.get(typeElement);

            // 메서드 생성
            final MethodSpec pullout = MethodSpec.methodBuilder("pullout")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "Rabbit!")
                    .build();

            // 클래스 생성
            final TypeSpec magicHat = TypeSpec.classBuilder("MagicHat")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(pullout)
                    .addSuperinterface(className)
                    .build();

            final Filer filer = processingEnv.getFiler();
            try {
                // Java 소스 코드 생성
                JavaFile.builder(className.packageName(), magicHat)
                        .build()
                        .writeTo(filer);
            } catch (IOException ex) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.toString());
            }
        }
        return true;
    }
}
