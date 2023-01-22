package org.xpdojo.autoconfigure.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class PropertyPlaceholderConfig {

    /**
     * PropertySourcesPlaceholderConfigurer 는 @Value 어노테이션을 사용할 때
     * 프로퍼티 파일을 읽어서 값을 주입해주는 역할을 한다.
     *
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor
     * @see org.springframework.core.io.support.PropertiesLoaderSupport
     */
    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
