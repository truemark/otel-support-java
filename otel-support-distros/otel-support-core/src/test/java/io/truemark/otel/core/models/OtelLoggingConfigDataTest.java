package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OtelLoggingConfigDataTest {

  @ParameterizedTest
  @CsvSource({"true, true", "true, false", "false, true", "false, false"})
  public void test_OtelLoggingConfigDataConstructorAndGetters_givenVaryingInputs(
      boolean loggingEnabled, boolean batchingEnabled) {
    OtelLoggingConfigData loggingConfig =
        new OtelLoggingConfigData(loggingEnabled, batchingEnabled);
    assertNotNull(loggingConfig.toString());
    assertEquals(loggingEnabled, loggingConfig.isLoggingEnabled());
    assertEquals(batchingEnabled, loggingConfig.isBatchingEnabled());
  }
}
