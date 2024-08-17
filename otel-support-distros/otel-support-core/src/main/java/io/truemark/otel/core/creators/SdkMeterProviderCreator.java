package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;

public final class SdkMeterProviderCreator {

  private SdkMeterProviderCreator() {
    super();
  }

  public static SdkMeterProvider createMeterProvider(
      final Resource resource, final MetricExporter metricExporter) {
    return SdkMeterProvider.builder()
        .registerMetricReader(PeriodicMetricReader.builder(metricExporter).build())
        .setResource(resource)
        .build();
  }
}
