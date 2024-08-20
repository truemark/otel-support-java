package io.truemark.otel.core.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.models.OtelTracingConfigData;
import io.truemark.otel.core.models.TraceSpanExporter;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SdkTracerProviderCreatorTest {

  static Stream<Arguments> batchingEnabledProvider() {
    final SpanExporter mockSpanExporter = mock(SpanExporter.class);
    return Stream.of(
        Arguments.of(
            new OtelTracingConfigData(
                true, Collections.singletonList(new TraceSpanExporter(true, mockSpanExporter)))),
        Arguments.of(
            new OtelTracingConfigData(
                true, Collections.singletonList(new TraceSpanExporter(false, mockSpanExporter)))),
        Arguments.of(new OtelTracingConfigData(true, Collections.emptyList())),
        Arguments.of(new OtelTracingConfigData(true, Collections.emptyList())));
  }

  @ParameterizedTest
  @MethodSource("batchingEnabledProvider")
  public void test_createTracerProvider_givenVaryingInputs(
      final OtelTracingConfigData tracingConfig) {
    Resource mockResource = mock(Resource.class);
    SdkTracerProvider tracerProvider =
        SdkTracerProviderCreator.createTracerProvider(mockResource, tracingConfig);

    assertNotNull(tracerProvider);
  }
}
