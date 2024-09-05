package io.truemark.otel.core.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.models.OtelOtlpConfigData;
import io.truemark.otel.core.models.OtelTracingConfigData;
import io.truemark.otel.core.models.SpanExporterHolder;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SdkTracerProviderCreatorTest {

  static Stream<Arguments> batchingEnabledProvider() {
    final SpanExporter mockSpanExporter = mock(SpanExporter.class);
    final OtelOtlpConfigData otlpConfig = new OtelOtlpConfigData(false, null);
    return Stream.of(
        Arguments.of(
            new OtelTracingConfigData(
                true,
                Collections.singletonList(new SpanExporterHolder(true, mockSpanExporter)),
                otlpConfig)),
        Arguments.of(
            new OtelTracingConfigData(
                true,
                Collections.singletonList(new SpanExporterHolder(false, mockSpanExporter)),
                otlpConfig)),
        Arguments.of(new OtelTracingConfigData(true, Collections.emptyList(), otlpConfig)),
        Arguments.of(new OtelTracingConfigData(false, Collections.emptyList(), otlpConfig)));
  }

  @ParameterizedTest
  @MethodSource("batchingEnabledProvider")
  void test_createTracerProvider_givenVaryingInputs(final OtelTracingConfigData tracingConfig) {
    Resource mockResource = mock(Resource.class);
    SdkTracerProvider tracerProvider =
        SdkTracerProviderCreator.createTracerProvider(mockResource, tracingConfig);

    assertNotNull(tracerProvider);
  }
}
