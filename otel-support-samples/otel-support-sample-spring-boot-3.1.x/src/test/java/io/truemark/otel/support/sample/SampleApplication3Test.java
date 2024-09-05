package io.truemark.otel.support.sample;

import io.truemark.otel.support.sample.controllers.TestController3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleApplication3Test {

  @Autowired private TestController3 testController3;

  @Test
  void test() {
    Assertions.assertNotNull(testController3.test());
  }
}
