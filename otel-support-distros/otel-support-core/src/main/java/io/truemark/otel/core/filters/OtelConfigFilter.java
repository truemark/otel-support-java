// OtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.Arrays;
import java.util.List;

public interface OtelConfigFilter {
  List<OtelConfigFilter> REGISTERED_OTEL_CONFIG_FILTERS =
      Arrays.asList(
          new TracingOtelConfigFilter(), new MetricsOtelConfigFilter(), new LoggingOtelConfigFilter());

  void apply(OpenTelemetrySdkBuilder builder, Resource resource, OpenTelemetrySetupData setupData);
}
