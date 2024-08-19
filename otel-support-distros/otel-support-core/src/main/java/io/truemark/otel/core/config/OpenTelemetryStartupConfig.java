package io.truemark.otel.core.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.instrumentation.resources.ContainerResource;
import io.opentelemetry.instrumentation.resources.HostResource;
import io.opentelemetry.instrumentation.resources.OsResource;
import io.opentelemetry.instrumentation.resources.ProcessResource;
import io.opentelemetry.instrumentation.resources.ProcessRuntimeResource;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.ServiceAttributes;
import io.truemark.otel.core.filters.LoggingOtelConfigFilter;
import io.truemark.otel.core.filters.MetricsOtelConfigFilter;
import io.truemark.otel.core.filters.OtelConfigFilterChain;
import io.truemark.otel.core.filters.TracingOtelConfigFilter;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class OpenTelemetryStartupConfig {

  private static final Logger log = Logger.getLogger(OpenTelemetryStartupConfig.class.getName());
  private static final AttributeKey<String> SERVICE_NAME_KEY = ServiceAttributes.SERVICE_NAME;
  private static final AttributeKey<String> SERVICE_VERSION_KEY = ServiceAttributes.SERVICE_VERSION;

  private final OpenTelemetrySetupData otelSetupData;
  private OpenTelemetry openTelemetry;

  public OpenTelemetryStartupConfig(final OpenTelemetrySetupData otelSetupData) {
    this.otelSetupData =
        Objects.requireNonNull(otelSetupData, "OpenTelemetrySetupData must be provided");
    this.initialize();
  }

  private void initialize() {

    final OtelConfigFilterChain filterChain =
        new OtelConfigFilterChain(
            Arrays.asList(
                new TracingOtelConfigFilter(),
                new MetricsOtelConfigFilter(),
                new LoggingOtelConfigFilter()));
    final OpenTelemetrySdkBuilder openTelemetryBuilder = OpenTelemetrySdk.builder();
    final Resource resource = createResource(otelSetupData.getAdditionalResources());

    filterChain.doFilter(openTelemetryBuilder, resource, otelSetupData);

    openTelemetryBuilder.setPropagators(
        ContextPropagators.create(W3CTraceContextPropagator.getInstance()));
    this.openTelemetry = openTelemetryBuilder.buildAndRegisterGlobal();
  }

  private Resource createResource(List<Resource> additionalResources) {

    final Resource resource =
        Resource.getDefault()
            .merge(ContainerResource.get())
            .merge(HostResource.get())
            .merge(OsResource.get())
            .merge(ProcessResource.get())
            .merge(ProcessRuntimeResource.get())
            .merge(
                Resource.create(
                    Attributes.builder()
                        .put(SERVICE_NAME_KEY, otelSetupData.getServiceConfig().getServiceName())
                        .put(
                            SERVICE_VERSION_KEY,
                            otelSetupData.getServiceConfig().getServiceVertion())
                        .build()));
    if (additionalResources != null && !additionalResources.isEmpty()) {
      additionalResources.forEach(resource::merge);
    }
    return resource;
  }

  public OpenTelemetry getOpenTelemetry() {
    return openTelemetry;
  }
}
