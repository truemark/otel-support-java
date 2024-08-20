package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OtelOtlpConfigDataTest {

  @ParameterizedTest
  @CsvSource({"true, http://localhost:4317", "false, ", "false, http://localhost:4317"})
  void test_OtelOtlpConfigDataConstructorAndGetters_givenValidInputs(
      boolean otlpEnabled, String otlpEndpoint) {
    OtelOtlpConfigData otlpConfig = new OtelOtlpConfigData(otlpEnabled, otlpEndpoint);
    assertNotNull(otlpConfig.toString());
    assertEquals(otlpEnabled, otlpConfig.isOtlpEnabled());
    assertEquals(otlpEndpoint, otlpConfig.getOtlpEndpoint());
  }

  @ParameterizedTest
  @CsvSource({"true, ", "true, ''"})
  void test_OtelOtlpConfigDataConstructor_givenInvalidInputs(
      boolean otlpEnabled, String otlpEndpoint) {
    assertThrows(
        IllegalArgumentException.class, () -> new OtelOtlpConfigData(otlpEnabled, otlpEndpoint));
  }
}
