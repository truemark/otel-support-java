// OtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.OpenTelemetrySetupData;

public interface OtelConfigFilter {

  void apply(
      OpenTelemetrySdkBuilder builder,
      Resource resource,
      OpenTelemetrySetupData setupData,
      OtelConfigFilterChain filterChain);
}
