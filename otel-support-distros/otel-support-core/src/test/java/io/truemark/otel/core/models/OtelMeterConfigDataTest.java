package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OtelMeterConfigDataTest {

  @ParameterizedTest
  @MethodSource("provideMeterConfigs")
  public void test_OtelMeterConfigDataConstructorAndGetters_givenVaryingInputs(
      final boolean isMeterEnabled, final List<MetricExporterHolder> metricExporterHolders) {
    OtelMeterConfigData meterConfig =
        new OtelMeterConfigData(isMeterEnabled, metricExporterHolders);
    assertNotNull(meterConfig.toString());
    assertEquals(isMeterEnabled, meterConfig.isMeterEnabled());
  }

  static Stream<Arguments> provideMeterConfigs() {
    return Stream.of(
        Arguments.of(
            true,
            Collections.singletonList(new MetricExporterHolder(LoggingMetricExporter.create()))),
        Arguments.of(true, Collections.emptyList()),
        Arguments.of(
            false,
            Collections.singletonList(new MetricExporterHolder(LoggingMetricExporter.create()))),
        Arguments.of(false, Collections.emptyList()));
  }
}
