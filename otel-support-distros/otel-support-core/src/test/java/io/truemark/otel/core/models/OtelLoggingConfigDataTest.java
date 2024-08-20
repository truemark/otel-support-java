package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import java.util.Collections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OtelLoggingConfigDataTest {

  @ParameterizedTest
  @CsvSource({"true, true", "true, false", "false, true", "false, false"})
  public void test_OtelLoggingConfigDataConstructorAndGetters_givenVaryingInputs(
      boolean loggingEnabled, boolean batchingEnabled) {
    OtelLoggingConfigData loggingConfig =
        new OtelLoggingConfigData(
            loggingEnabled,
            Collections.singletonList(
                new LogRecordExporterHolder(batchingEnabled, mock(LogRecordExporter.class))));
    assertNotNull(loggingConfig.toString());
    assertEquals(loggingEnabled, loggingConfig.isLoggingEnabled());
    assertEquals(
        batchingEnabled, loggingConfig.getLogRecordExporterHolders().get(0).isBatchingEnabled());
  }
}
