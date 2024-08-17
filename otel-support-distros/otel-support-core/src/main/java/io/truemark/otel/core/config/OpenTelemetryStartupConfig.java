package io.truemark.otel.core.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.ServiceAttributes;
import io.truemark.otel.core.filters.OpenTelemetryConfigFilter;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class OpenTelemetryStartupConfig {

  private static final Logger log = Logger.getLogger(OpenTelemetryStartupConfig.class.getName());
  private static final AttributeKey<String> SERVICE_NAME_KEY = ServiceAttributes.SERVICE_NAME;
  private static final AttributeKey<String> SERVICE_VERSION_KEY = ServiceAttributes.SERVICE_VERSION;

  private final OpenTelemetrySetupData otelSetupData;
  private OpenTelemetry openTelemetry;
  private final List<OpenTelemetryConfigFilter> filters;

  public OpenTelemetryStartupConfig(final OpenTelemetrySetupData otelSetupData) {
    this.otelSetupData =
        Objects.requireNonNull(otelSetupData, "OpenTelemetrySetupData must be provided");
    this.filters = OpenTelemetryConfigFilter.REGISTERED_OTEL_CONFIG_FILTERS;
    this.initialize();
  }

  private void initialize() {
    final OpenTelemetrySdkBuilder openTelemetryBuilder = OpenTelemetrySdk.builder();
    final Resource resource = createResource();

    for (OpenTelemetryConfigFilter filter : filters) {
      filter.apply(openTelemetryBuilder, resource, otelSetupData);
    }

    openTelemetryBuilder.setPropagators(
        ContextPropagators.create(W3CTraceContextPropagator.getInstance()));
    this.openTelemetry = openTelemetryBuilder.buildAndRegisterGlobal();
  }

  private Resource createResource() {
    return Resource.getDefault().toBuilder()
        .put(SERVICE_NAME_KEY, otelSetupData.getServiceConfig().getServiceName())
        .put(SERVICE_VERSION_KEY, otelSetupData.getServiceConfig().getServiceVertion())
        .build();
  }

  public OpenTelemetry getOpenTelemetry() {
    return openTelemetry;
  }
}
