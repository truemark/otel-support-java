package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.SdkMeterProviderBuilder;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.MetricView;
import java.util.List;

public final class SdkMeterProviderCreator {

  private SdkMeterProviderCreator() {
    super();
  }

  public static SdkMeterProvider createMeterProvider(
      final Resource resource, final MetricExporter metricExporter, List<MetricView> metricViews) {
    final SdkMeterProviderBuilder meterProviderBuilder =
        SdkMeterProvider.builder()
            .registerMetricReader(PeriodicMetricReader.builder(metricExporter).build())
            .setResource(resource);
    if (metricViews != null && !metricViews.isEmpty()) {
      metricViews.forEach(
          metricView ->
              meterProviderBuilder.registerView(metricView.getSelector(), metricView.getView()));
    }

    return meterProviderBuilder.build();
  }
}
