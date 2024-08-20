package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.MetricExporterHolder;
import java.util.List;

public interface OtelMetricExportersRegistry {
  List<MetricExporterHolder> getRegisteredMetricExporters();
}
