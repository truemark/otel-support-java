package io.truemark.otel.core.models;

import io.opentelemetry.api.internal.StringUtils;

public class OtelOtlpConfigData {

  private final boolean otlpEnabled;
  private final String otlpEndpoint;

  public OtelOtlpConfigData(final boolean otlpEnabled, final String otlpEndpoint) {
    if (otlpEnabled && StringUtils.isNullOrEmpty(otlpEndpoint)) {
      throw new IllegalArgumentException("OTLP endpoint must be provided when OTLP is enabled");
    }
    this.otlpEnabled = otlpEnabled;
    this.otlpEndpoint = otlpEndpoint;
  }

  public boolean isOtlpEnabled() {
    return otlpEnabled;
  }

  public String getOtlpEndpoint() {
    return otlpEndpoint;
  }

  @Override
  public String toString() {
    return "OtelOtlpConfigData{"
        + "otlpEnabled="
        + otlpEnabled
        + ", otlpEndpoint='"
        + otlpEndpoint
        + '\''
        + '}';
  }
}
