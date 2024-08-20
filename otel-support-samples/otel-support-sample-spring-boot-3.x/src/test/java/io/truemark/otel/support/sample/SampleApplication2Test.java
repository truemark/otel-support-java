package io.truemark.otel.support.sample;

import io.truemark.otel.support.sample.controllers.TestController2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleApplication2Test {

    @Autowired
    private TestController2 testController2;

    @Test
    void test() {
        Assertions.assertNotNull(testController2.test());
    }
}
