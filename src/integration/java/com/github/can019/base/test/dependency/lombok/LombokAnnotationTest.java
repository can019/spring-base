package com.github.can019.base.test.dependency.lombok;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class LombokAnnotationTest {

    @Test
    @DisplayName("Integration test의 test code에서 `@Slf4j`를 사용할 수 있어야 한다")
    void lombokSlf4jAvailableTest(){
        assertThat(log).isNotNull();
    }
}
