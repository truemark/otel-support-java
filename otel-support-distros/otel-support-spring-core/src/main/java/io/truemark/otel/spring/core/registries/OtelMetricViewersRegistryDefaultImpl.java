package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.MetricViewHolder;
import java.util.Collections;
import java.util.List;

public class OtelMetricViewersRegistryDefaultImpl implements OtelMetricViewersRegistry {
  @Override
  public List<MetricViewHolder> getRegisteredMetricViews() {
    return Collections.emptyList();
  }
}
