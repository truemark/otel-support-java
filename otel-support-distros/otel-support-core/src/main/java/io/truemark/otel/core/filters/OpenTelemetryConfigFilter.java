// OpenTelemetryConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.Arrays;
import java.util.List;

public interface OpenTelemetryConfigFilter {
  List<OpenTelemetryConfigFilter> REGISTERED_OTEL_CONFIG_FILTERS =
      Arrays.asList(
          new TracingConfigFilter(), new MetricsConfigFilter(), new LoggingConfigFilter());

  void apply(OpenTelemetrySdkBuilder builder, Resource resource, OpenTelemetrySetupData setupData);
}
