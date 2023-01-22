package org.xpdojo.webspringcontainer.container;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.xpdojo.autoconfiguration.MyAutoConfiguration;

import java.util.stream.StreamSupport;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    // org.springframework.beans.factory.BeanClassLoaderAware
    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // return new String[]{
        //         org.xpdojo.autoconfiguration.WebServerConfiguration.class.getName(),
        //         org.xpdojo.autoconfiguration.DispatcherServletConfiguration.class.getName(),
        // };
        ImportCandidates load = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
        return StreamSupport.stream(load.spliterator(), false)
                .toArray(String[]::new);
    }
}
