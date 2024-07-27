package com.github.can019.fail;

import org.junit.jupiter.api.Test;

public class FailTest {

    @Test
    void fail(){
        throw new RuntimeException("Fail");
    }
}
