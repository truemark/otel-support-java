package io.truemark.otel.core.models;

import io.opentelemetry.sdk.resources.Resource;
import java.util.List;
import java.util.Objects;

public class OpenTelemetrySetupData {

  private final OtelServiceConfigData serviceConfig;
  private final OtelOtlpConfigData otlpConfig;
  private final OtelTracingConfigData otelTracingConfig;
  private final OtelMeterConfigData otelMeterConfig;
  private final OtelLoggingConfigData otelLoggingConfig;
  private List<Resource> additionalResources;

  public OpenTelemetrySetupData(
      final OtelServiceConfigData serviceConfig,
      final OtelOtlpConfigData otlpConfig,
      final OtelTracingConfigData otelTracingConfig,
      OtelMeterConfigData otelMeterConfig,
      final OtelLoggingConfigData otelLoggingConfig) {

    Objects.requireNonNull(serviceConfig, "Service config must be provided");
    Objects.requireNonNull(otlpConfig, "OTLP config must be provided");
    Objects.requireNonNull(otelTracingConfig, "Tracing config must be provided");
    Objects.requireNonNull(otelMeterConfig, "Meter config must be provided");
    Objects.requireNonNull(otelLoggingConfig, "Logging config must be provided");
    this.serviceConfig = serviceConfig;
    this.otlpConfig = otlpConfig;
    this.otelTracingConfig = otelTracingConfig;
    this.otelMeterConfig = otelMeterConfig;
    this.otelLoggingConfig = otelLoggingConfig;
  }

  public OtelServiceConfigData getServiceConfig() {
    return serviceConfig;
  }

  public OtelOtlpConfigData getOtlpConfig() {
    return otlpConfig;
  }

  public OtelTracingConfigData getOtelTracingConfig() {
    return otelTracingConfig;
  }

  public OtelMeterConfigData getOtelMeterConfig() {
    return otelMeterConfig;
  }

  public OtelLoggingConfigData getOtelLoggingConfig() {
    return otelLoggingConfig;
  }

  @Override
  public String toString() {
    return "OpenTelemetrySetupData{"
        + "serviceConfig="
        + serviceConfig
        + ", otlpConfig="
        + otlpConfig
        + ", otelTracingConfig="
        + otelTracingConfig
        + ", otelMeterConfig="
        + otelMeterConfig
        + ", otelLoggingConfig="
        + otelLoggingConfig
        + '}';
  }

  public List<Resource> getAdditionalResources() {
    return additionalResources;
  }

  public void setAdditionalResources(List<Resource> additionalResources) {
    this.additionalResources = additionalResources;
  }
}
