package io.truemark.otel.spring.boot.config;

import io.opentelemetry.api.OpenTelemetry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = OpenTelemetryAutoconfiguration.class)
@TestPropertySource(
    properties = {
      "truemark.otel.service.name=otel-test-service",
      "truemark.otel.service.version=1.0.0"
    })
public class OpenTelemetryAutoconfigurationTest {

  @Autowired private OpenTelemetry openTelemetry;

  @Test
  void contextLoads() {
    Assertions.assertThat(openTelemetry).isNotNull();
  }
}
