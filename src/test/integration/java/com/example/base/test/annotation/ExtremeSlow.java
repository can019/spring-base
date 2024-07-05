package com.example.base.test.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Tag("Extreme-slow")
public @interface ExtremeSlow {
}
