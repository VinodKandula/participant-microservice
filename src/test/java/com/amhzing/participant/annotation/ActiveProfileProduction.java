package com.amhzing.participant.annotation;

import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Inherited
@ActiveProfiles("production")
public @interface ActiveProfileProduction {

}
