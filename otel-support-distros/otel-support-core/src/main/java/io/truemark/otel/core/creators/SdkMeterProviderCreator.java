package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.SdkMeterProviderBuilder;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.MetricExporterHolder;
import io.truemark.otel.core.models.MetricViewHolder;
import io.truemark.otel.core.models.OtelMeterConfigData;
import java.util.List;

public final class SdkMeterProviderCreator {

  private SdkMeterProviderCreator() {
    super();
  }

  public static SdkMeterProvider createMeterProvider(
      final Resource defaultResource, final OtelMeterConfigData otelMeterConfig) {
    final List<MetricExporterHolder> metricExporters = otelMeterConfig.getMetricExporterHolders();
    final List<MetricViewHolder> metricViewHolders = otelMeterConfig.getMetricViewHolders();
    final SdkMeterProviderBuilder meterProviderBuilder = SdkMeterProvider.builder();

    if (metricExporters != null && !metricExporters.isEmpty()) {
      metricExporters.forEach(
          metricExporterHolder -> {
            final MetricExporter metricExporter = metricExporterHolder.getMetricExporter();
            meterProviderBuilder.registerMetricReader(
                PeriodicMetricReader.builder(metricExporter).build());
            if (metricExporterHolder.getResource() != null) {
              meterProviderBuilder.addResource(metricExporterHolder.getResource());
            }
          });
    }

    if (metricViewHolders != null && !metricViewHolders.isEmpty()) {
      metricViewHolders.forEach(
          metricViewHolder ->
              meterProviderBuilder.registerView(
                  metricViewHolder.getSelector(), metricViewHolder.getView()));
    }

    if (defaultResource != null) {
      meterProviderBuilder.addResource(defaultResource);
    }

    return meterProviderBuilder.build();
  }
}
