package io.truemark.otel.spring.core.utils;

public class OtelCustomProperties {

  private OtelCustomProperties() {
    super();
  }

  public static final String OTEL_SERVICE_NAME = "otel.service.name";
  public static final String OTEL_SERVICE_VERSION = "otel.service.version";
  public static final String OTEL_OTLP_ENDPOINT = "otel.exporter.otlp.endpoint";
  public static final String OTEL_TRACES_EXPORTER = "otel.traces.exporter";
  public static final String OTEL_METRICS_EXPORTER = "otel.metrics.exporter";
  public static final String OTEL_LOGS_EXPORTER = "otel.logs.exporter";

  public static final String EXPORTER_CONSOLE = "console";
  public static final String EXPORTER_OTLP = "otlp";
  public static final String EXPORTER_NONE = "none";
}
