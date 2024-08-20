package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.MetricExporterHolder;
import java.util.Collections;
import java.util.List;

public class OtelMetricExportersRegistryDefaultImpl implements OtelMetricExportersRegistry {

  @Override
  public List<MetricExporterHolder> getRegisteredMetricExporters() {
    return Collections.emptyList();
  }
}
