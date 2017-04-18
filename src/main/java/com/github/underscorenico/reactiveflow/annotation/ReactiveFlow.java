package com.github.underscorenico.reactiveflow.annotation;

import com.github.underscorenico.reactiveflow.ReactiveFlowApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ReactiveFlowApplication.class})
@Documented
public @interface ReactiveFlow {

	String[] value() default {};
}
