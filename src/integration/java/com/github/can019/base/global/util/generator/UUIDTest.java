package com.github.can019.base.global.util.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

public class UUIDTest {


    @Test
    @Disabled("외부 라이브러리로 test x")
    void uuidV1은_uuid_스펙과_같아야한다(){
        java.util.UUID uuidv1 = com.github.can019.global.util.generator.UUID.generateSequentialUUIDV1();
    }

    @Test
    void 하이픈이_제거된_sequential_uuid는_늦게만들어진_것이_더_커야한다(){
        List<String> originArrayList = new ArrayList<>();
        final int TEST_TIME = 1000000;

        for(int i = 0; i < TEST_TIME; i++){
            originArrayList.add(com.github.can019.global.util.generator.UUID.generateSequentialUUIDV1WithoutHyphen());
        }
        List<String> sortedArrayList = new ArrayList(originArrayList);
        Collections.sort(sortedArrayList);

        Assertions.assertEquals(originArrayList, sortedArrayList);

    }
}
