package io.truemark.otel.core.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.LogRecordExporterHolder;
import io.truemark.otel.core.models.OtelLoggingConfigData;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SdkLoggerProviderCreatorTest {

  private static Stream<Arguments> loggerProvider() {
    LogRecordExporter mockLogRecordExporter = mock(LogRecordExporter.class);
    return Stream.of(
        Arguments.of(
            new OtelLoggingConfigData(
                true,
                Collections.singletonList(
                    new LogRecordExporterHolder(true, mockLogRecordExporter)))),
        Arguments.of(
            new OtelLoggingConfigData(
                true,
                Collections.singletonList(
                    new LogRecordExporterHolder(false, mockLogRecordExporter)))),
        Arguments.of(new OtelLoggingConfigData(true, Collections.emptyList())),
        Arguments.of(new OtelLoggingConfigData(true, Collections.emptyList())));
  }

  @ParameterizedTest
  @MethodSource("loggerProvider")
  public void test_createLoggerProvider_givenVaryingInputs(
      OtelLoggingConfigData otelLoggingConfig) {
    Resource mockResource = mock(Resource.class);

    SdkLoggerProvider loggerProvider =
        SdkLoggerProviderCreator.createLoggerProvider(mockResource, otelLoggingConfig);

    assertNotNull(loggerProvider);
  }
}
