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

public class OtelTracingConfigDataTest {

  static Stream<Arguments> provideTracingConfigData() {
    TraceSpanExporter mockExporter1 = mock(TraceSpanExporter.class);
    TraceSpanExporter mockExporter2 = mock(TraceSpanExporter.class);
    Sampler mockSampler = mock(Sampler.class);

    return Stream.of(
        Arguments.of(true, Arrays.asList(mockExporter1, mockExporter2), Optional.of(mockSampler)),
        Arguments.of(true, Collections.emptyList(), Optional.empty()),
        Arguments.of(false, Collections.singletonList(mockExporter1), Optional.of(mockSampler)),
        Arguments.of(false, Collections.emptyList(), Optional.empty()));
  }

  @ParameterizedTest
  @MethodSource("provideTracingConfigData")
  public void test_OtelTracingConfigDataConstructorAndGetters_givenVaryingInputs(
      boolean tracingEnabled,
      List<TraceSpanExporter> traceSpanExporters,
      Optional<Sampler> sampler) {
    OtelTracingConfigData configData =
        new OtelTracingConfigData(tracingEnabled, traceSpanExporters);
    sampler.ifPresent(configData::setSampler);

    assertNotNull(configData.toString());
    assertEquals(tracingEnabled, configData.isTracingEnabled());
    assertEquals(traceSpanExporters, configData.getTraceSpanExporters());
    assertEquals(sampler.orElse(null), configData.getSampler());
  }
}
