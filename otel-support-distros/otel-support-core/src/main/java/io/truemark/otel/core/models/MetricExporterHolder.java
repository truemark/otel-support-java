package io.truemark.otel.core.models;

import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.resources.Resource;

public class MetricExporterHolder {

  private final MetricExporter metricExporter;
  private Resource resource;

  public MetricExporterHolder(MetricExporter metricExporter) {
    this.metricExporter = metricExporter;
  }

  public MetricExporter getMetricExporter() {
    return metricExporter;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  @Override
  public String toString() {
    return "MetricExporterHolder{"
        + "metricExporter="
        + metricExporter
        + ", resource="
        + resource
        + '}';
  }
}
