package io.truemark.otel.spring.core.api;

import io.truemark.otel.core.models.MetricViewHolder;
import java.util.List;

public interface OtelMetricViewersRegistry {
  List<MetricViewHolder> getRegisteredMetricViews();
}
