package io.truemark.otel.core.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.stream.Stream;

import io.truemark.otel.core.models.OtelTracingConfigData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SdkTracerProviderCreatorTest {

  static Stream<Arguments> batchingEnabledProvider() {
    return Stream.of(
            Arguments.of(new OtelTracingConfigData(true, true)),
            Arguments.of(new OtelTracingConfigData(true, false))
    );
  }

  @ParameterizedTest
  @MethodSource("batchingEnabledProvider")
  public void test_createTracerProvider_givenVaryingInputs(final OtelTracingConfigData tracingConfig) {
    Resource mockResource = mock(Resource.class);
    SpanExporter mockSpanExporter = mock(SpanExporter.class);

    SdkTracerProvider tracerProvider =
        SdkTracerProviderCreator.createTracerProvider(
            mockResource, mockSpanExporter, tracingConfig);

    assertNotNull(tracerProvider);
  }
}
