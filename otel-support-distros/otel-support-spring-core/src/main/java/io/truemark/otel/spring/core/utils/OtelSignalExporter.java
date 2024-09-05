// OtelSignalExporter.java
package io.truemark.otel.spring.core.utils;

import static io.truemark.otel.spring.core.utils.OtelCustomProperties.*;

public enum OtelSignalExporter {
  LOGS_CONSOLE(OTEL_LOGS_EXPORTER, EXPORTER_CONSOLE, true, false, false),
  LOGS_OTLP(OTEL_LOGS_EXPORTER, EXPORTER_OTLP, true, false, false),
  LOGS_NONE(OTEL_LOGS_EXPORTER, EXPORTER_NONE, false, false, false),

  METRICS_CONSOLE(OTEL_METRICS_EXPORTER, EXPORTER_CONSOLE, false, true, false),
  METRICS_OTLP(OTEL_METRICS_EXPORTER, EXPORTER_OTLP, false, true, false),
  METRICS_NONE(OTEL_METRICS_EXPORTER, EXPORTER_NONE, false, false, false),

  TRACES_CONSOLE(OTEL_TRACES_EXPORTER, EXPORTER_CONSOLE, false, false, true),
  TRACES_OTLP(OTEL_TRACES_EXPORTER, EXPORTER_OTLP, false, false, true),
  TRACES_NONE(OTEL_TRACES_EXPORTER, EXPORTER_NONE, false, false, false),

  NONE(EXPORTER_NONE, EXPORTER_NONE, false, false, false);

  private final String signalExporterConfigName;
  private final String signalExporterType;
  private final boolean logsEnabled;
  private final boolean metricsEnabled;
  private final boolean tracesEnabled;

  private OtelSignalExporter(
      final String signalExporterConfigName,
      final String signalExporterType,
      boolean logsEnabled,
      boolean metricsEnabled,
      boolean tracesEnabled) {
    this.signalExporterConfigName = signalExporterConfigName;
    this.signalExporterType = signalExporterType;
    this.logsEnabled = logsEnabled;
    this.metricsEnabled = metricsEnabled;
    this.tracesEnabled = tracesEnabled;
  }

  public String getSignalExporterConfigName() {
    return signalExporterConfigName;
  }

  public String getSignalExporterType() {
    return signalExporterType;
  }

  public boolean isLogsEnabled() {
    return logsEnabled;
  }

  public boolean isMetricsEnabled() {
    return metricsEnabled;
  }

  public boolean isTracesEnabled() {
    return tracesEnabled;
  }

  public static OtelSignalExporter fromSignalExporterConfigNameAndType(
      final String signalExporterConfigName, final String signalExporterType) {
    for (OtelSignalExporter exporter : values()) {
      if (exporter.getSignalExporterConfigName().equals(signalExporterConfigName)
          && exporter.getSignalExporterType().equals(signalExporterType)) {
        return exporter;
      }
    }
    return NONE;
  }

  public boolean isOTLPEnabled() {
    return EXPORTER_OTLP.equalsIgnoreCase(signalExporterType);
  }

  public boolean isConsoleEnabled() {
    return EXPORTER_CONSOLE.equalsIgnoreCase(signalExporterType);
  }
}
