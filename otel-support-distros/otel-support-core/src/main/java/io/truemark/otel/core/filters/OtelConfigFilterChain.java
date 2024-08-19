package io.truemark.otel.core.filters;

import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.List;

public class OtelConfigFilterChain {
  private List<OtelConfigFilter> filters;
  private int currentIndex = 0;

  public OtelConfigFilterChain(List<OtelConfigFilter> filters) {
    this.filters = filters;
  }

  public void doFilter(
      OpenTelemetrySdkBuilder builder, Resource resource, OpenTelemetrySetupData setupData) {
    if (currentIndex < filters.size()) {
      filters.get(currentIndex++).apply(builder, resource, setupData, this);
    }
  }
}
