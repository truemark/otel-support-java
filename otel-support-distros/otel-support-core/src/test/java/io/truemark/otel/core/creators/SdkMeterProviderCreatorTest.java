package io.truemark.otel.core.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.MetricExporterHolder;
import io.truemark.otel.core.models.OtelMeterConfigData;
import io.truemark.otel.core.models.OtelOtlpConfigData;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SdkMeterProviderCreatorTest {

  static Stream<MetricExporter> metricExporterProvider() {
    return Stream.of(LoggingMetricExporter.create());
  }

  @ParameterizedTest
  @MethodSource("metricExporterProvider")
  void test_createMeterProvider_givenVaryingInputs(MetricExporter metricExporter) {
    Resource mockResource = mock(Resource.class);

    OtelMeterConfigData otelMeterConfigData =
        new OtelMeterConfigData(
            true,
            Collections.singletonList(new MetricExporterHolder(metricExporter)),
            new OtelOtlpConfigData(false, null));
    SdkMeterProvider meterProvider =
        SdkMeterProviderCreator.createMeterProvider(mockResource, otelMeterConfigData);

    assertNotNull(meterProvider);
  }
}
