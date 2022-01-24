package com.markruler.demo.fixtures;

import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;
import org.hibernate.validator.internal.engine.valuecontext.BeanValueContext;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.MethodParameter;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class Hello {

    /**
     * Validation 처리 흐름<br/>
     * <br/>
     * Interceptor를 지나서
     * {@link org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)}
     * 이 가장 먼저 Request를 처리한다.
     * <p>Mapping된 핸들러를 실행한다.
     * {@link org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter#handle(HttpServletRequest, HttpServletResponse, Object)}
     * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#handleInternal(HttpServletRequest, HttpServletResponse, HandlerMethod)}
     * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#invokeHandlerMethod(HttpServletRequest, HttpServletResponse, HandlerMethod)}
     * {@link org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod#invokeAndHandle(ServletWebRequest, ModelAndViewContainer, Object...)}
     * {@link org.springframework.web.method.support.InvocableHandlerMethod#invokeForRequest(NativeWebRequest, ModelAndViewContainer, Object...)}
     * </p>
     * <p>현재 Request에서 method argument 값을 얻는다.
     * {@link org.springframework.web.method.support.InvocableHandlerMethod#getMethodArgumentValues(NativeWebRequest, ModelAndViewContainer, Object...)}
     * {@link org.springframework.web.method.support.HandlerMethodArgumentResolverComposite#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)}
     * {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)}
     * </p>
     * <p>검증할 argument 값을 얻는다.
     * {@link org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver#validateIfApplicable(WebDataBinder, MethodParameter)}
     * "javax.validation.Valid".equals(annotationName) 조건이 참이면 validate한다.
     * {@link org.springframework.validation.annotation.ValidationAnnotationUtils#determineValidationHints(Annotation)}
     * {@link org.springframework.validation.DataBinder#validate(Object...)}
     * {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter#validate(Object, Errors)}
     * {@link org.hibernate.validator.internal.engine.ValidatorImpl#validate(Object, Class[])}
     * {@link org.hibernate.validator.internal.engine.ValidatorImpl#validateInContext(BaseBeanValidationContext, BeanValueContext, ValidationOrder)}
     * Validation 조건을 위반한 경우
     * {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter#processConstraintViolations(Set, Errors)}
     * </p>
     * <p>
     * {@link org.springframework.web.method.support.InvocableHandlerMethod#invokeForRequest(NativeWebRequest, ModelAndViewContainer, Object...)}로
     * 돌아가서 요청을 처리한다.
     * {@link org.springframework.web.method.support.InvocableHandlerMethod#doInvoke(Object...)}
     * </p>
     * <p>
     * Proxy 객체룰 통해 메서드를 실행한다.
     * {@link java.lang.reflect.Method#invoke(Object, Object...)}
     * {@link jdk.internal.reflect.DelegatingMethodAccessorImpl#invoke(Object, Object[])}
     * {@link jdk.internal.reflect.NativeMethodAccessorImpl#invoke(Object, Object[])}
     * {@link jdk.internal.reflect.NativeMethodAccessorImpl#invoke0(Method, Object, Object[])}
     * </p>
     * <p>obj = {DemoController$$EnhancerBySpringCGLIB$$a571f5f5@7848} "com.markruler.demo.controller.DemoController@19652d44"
     *  CGLIB$BOUND = false
     *  CGLIB$CALLBACK_0 = {CglibAopProxy$DynamicAdvisedInterceptor@8383}
     *  CGLIB$CALLBACK_1 = {CglibAopProxy$StaticUnadvisedInterceptor@8384}
     *  CGLIB$CALLBACK_2 = {CglibAopProxy$SerializableNoOp@8385}
     *  CGLIB$CALLBACK_3 = {CglibAopProxy$StaticDispatcher@8386}
     *  CGLIB$CALLBACK_4 = {CglibAopProxy$AdvisedDispatcher@8387}
     *  CGLIB$CALLBACK_5 = {CglibAopProxy$EqualsInterceptor@8388}
     *  CGLIB$CALLBACK_6 = {CglibAopProxy$HashCodeInterceptor@8389}
     *  </p>
     *  <p>Proxy 객체가 요청을 가로챈다.
     * {@link org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#intercept(Object, Method, Object[], MethodProxy)}
     * {@link org.springframework.aop.target.SingletonTargetSource#getTarget()}
     * </p>
     * <p>해당 메서드에 적용되는 Interceptor와 Advice를 찾는다.
     * {@link org.springframework.aop.framework.AdvisedSupport#getInterceptorsAndDynamicInterceptionAdvice(Method, Class)}
     * </p>
     * <p>순서대로 메서드를 실행한다.
     * {@link org.springframework.aop.framework.CglibAopProxy.CglibMethodInvocation#proceed()}
     * {@link org.springframework.aop.framework.ReflectiveMethodInvocation#proceed()}
     * {@link org.springframework.aop.interceptor.ExposeInvocationInterceptor#invoke(MethodInvocation)}
     * {@link org.springframework.aop.framework.CglibAopProxy#processReturnType(Object, Object, Method, Object)}
     * </p>
     */
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "username='" + username + '\'' +
                '}';
    }
}
