package io.truemark.otel.core.models;

import java.util.Objects;

public class OpenTelemetrySetupData {

  private final OtelServiceConfigData serviceConfig;
  private final OtelOtlpConfigData otlpConfig;
  private final OtelTracingConfigData otelTracingConfig;
  private final OtelLoggingConfigData otelLoggingConfig;

  public OpenTelemetrySetupData(
      OtelServiceConfigData serviceConfig,
      final OtelOtlpConfigData otlpConfig,
      final OtelTracingConfigData otelTracingConfig,
      final OtelLoggingConfigData otelLoggingConfig) {
    Objects.requireNonNull(serviceConfig, "Service config must be provided");
    Objects.requireNonNull(otlpConfig, "OTLP config must be provided");
    Objects.requireNonNull(otelTracingConfig, "Tracing config must be provided");
    Objects.requireNonNull(otelLoggingConfig, "Logging config must be provided");
    this.serviceConfig = serviceConfig;
    this.otlpConfig = otlpConfig;
    this.otelTracingConfig = otelTracingConfig;
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

  public OtelLoggingConfigData getOtelLoggingConfig() {
    return otelLoggingConfig;
  }
}
