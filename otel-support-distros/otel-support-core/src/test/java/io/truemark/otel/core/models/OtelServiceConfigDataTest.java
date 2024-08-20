package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OtelServiceConfigDataTest {

  @ParameterizedTest
  @CsvSource({"test-service, 1.0.0", "another-service, 2.1.3"})
  public void test_OtelServiceConfigDataConstructorAndGetters_givenValidInputs(
      String serviceName, String serviceVersion) {
    OtelServiceConfigData serviceConfig = new OtelServiceConfigData(serviceName, serviceVersion);
    assertNotNull(serviceConfig.toString());
    assertEquals(serviceName, serviceConfig.getServiceName());
    assertEquals(serviceVersion, serviceConfig.getServiceVersion());
  }

  @ParameterizedTest
  @CsvSource({"'', 1.0.0", "test-service, ''", "'', ''"})
  public void test_OtelServiceConfigDataConstructor_givenInvalidInputs(
      String serviceName, String serviceVersion) {
    assertThrows(
        IllegalArgumentException.class,
        () -> new OtelServiceConfigData(serviceName, serviceVersion));
  }
}
