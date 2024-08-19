package io.truemark.otel.core.filters;

import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class OtelConfigFilterChain {
  private static final Logger log = Logger.getLogger(OtelConfigFilterChain.class.getName());
  private final List<OtelConfigFilter> filters;
  private int currentIndex = 0;

  // Constructor to initialize the filter chain with a list of filters
  public OtelConfigFilterChain(List<OtelConfigFilter> filters) {
    this.filters = Collections.unmodifiableList(filters);
  }

  // Apply the filters in the chain to the OpenTelemetry SDK builder
  public void doFilter(
      OpenTelemetrySdkBuilder builder, Resource resource, OpenTelemetrySetupData setupData) {
    if (currentIndex < filters.size()) {
      try {
        filters.get(currentIndex++).apply(builder, resource, setupData, this);
      } catch (Exception e) {
        log.severe("Error applying filter: " + e.getMessage());
        throw e;
      }
    }
  }
}
