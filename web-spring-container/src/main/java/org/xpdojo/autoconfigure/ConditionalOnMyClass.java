package org.xpdojo.autoconfigure;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 주로 <code>@Configuration</code> 클래스 레벨에서 사용하지만
 * <code>@Bean</code> 메소드 레벨에서도 사용할 수 있다.
 * 단, 클래스 레벨의 검증 없이 <code>@Bean</code> 메소드 레벨에서만 사용하는 것은 추천하지 않는다.
 * 불필요하게 <code>@Configuration</code> 클래스가 빈으로 등록될 수 있기 때문이다.
 * <p>
 * <code>@Configuration</code> 클래스 레벨의 <code>@ConditionalOnClass</code>와
 * <code>@Bean</code> 메소드 레벨의 <code>@ConditionalOnMissingBean</code> 조합은
 * 가장 많이 사용되는 조합이다.
 * 클래스의 존재로 해당 기술의 사용 여부를 확인하고,
 * 직접 추가한 Custom Bean의 존재를 확인해서 Auto Configuration을 적용할지 결정한다.
 *
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnClass
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnBean
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Conditional(MyOnClassCondition.class)
public @interface ConditionalOnMyClass {
    String value();
}
