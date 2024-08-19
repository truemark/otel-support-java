package io.truemark.otel.spring.core.utils;

public class OtelCustomProperties {

  private OtelCustomProperties() {
    super();
  }

  public static final String OTEL_SERVICE_NAME = "truemark.otel.service.name";
  public static final String OTEL_SERVICE_VERSION = "truemark.otel.service.version";
  public static final String OTEL_OTLP_ENABLED = "truemark.otel.otlp.enabled";
  public static final String OTEL_OTLP_ENDPOINT = "truemark.otel.otlp.endpoint";
}
