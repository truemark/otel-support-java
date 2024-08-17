package io.truemark.otel.core.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.resources.Resource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SdkLoggerProviderCreatorTest {

  @ParameterizedTest
  @CsvSource({"true", "false"})
  public void test_createLoggerProvider_givenVaryingInputs(boolean isBatchingEnabled) {
    Resource mockResource = mock(Resource.class);
    LogRecordExporter mockLogRecordExporter = mock(LogRecordExporter.class);

    SdkLoggerProvider loggerProvider =
        SdkLoggerProviderCreator.createLoggerProvider(
            mockResource, mockLogRecordExporter, isBatchingEnabled);

    assertNotNull(loggerProvider);
  }
}
