package com.example.base.test.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag("Extreme-slow")
public @interface ExtremeSlow {
}
