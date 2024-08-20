package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OtelTracingConfigDataTest {

  static Stream<Arguments> provideTracingConfigData() {
    SpanExporterHolder mockExporter1 = mock(SpanExporterHolder.class);
    SpanExporterHolder mockExporter2 = mock(SpanExporterHolder.class);
    Sampler mockSampler = mock(Sampler.class);

    return Stream.of(
        Arguments.of(true, Arrays.asList(mockExporter1, mockExporter2), Optional.of(mockSampler)),
        Arguments.of(true, Collections.emptyList(), Optional.empty()),
        Arguments.of(false, Collections.singletonList(mockExporter1), Optional.of(mockSampler)),
        Arguments.of(false, Collections.emptyList(), Optional.empty()));
  }

  @ParameterizedTest
  @MethodSource("provideTracingConfigData")
  void test_OtelTracingConfigDataConstructorAndGetters_givenVaryingInputs(
      boolean tracingEnabled,
      List<SpanExporterHolder> spanExporterHolders,
      Optional<Sampler> sampler) {
    OtelTracingConfigData configData =
        new OtelTracingConfigData(tracingEnabled, spanExporterHolders);
    sampler.ifPresent(configData::setSampler);

    assertNotNull(configData.toString());
    assertEquals(tracingEnabled, configData.isTracingEnabled());
    assertEquals(spanExporterHolders, configData.getTraceSpanExporters());
    assertEquals(sampler.orElse(null), configData.getSampler());
  }
}
