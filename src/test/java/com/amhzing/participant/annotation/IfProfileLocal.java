package com.amhzing.participant.annotation;

import org.springframework.test.annotation.IfProfileValue;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Inherited
@IfProfileValue(name = "local")
public @interface IfProfileLocal {

}
