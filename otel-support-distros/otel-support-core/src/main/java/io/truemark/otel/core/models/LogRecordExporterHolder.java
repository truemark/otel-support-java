package io.truemark.otel.core.models;

import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.resources.Resource;

public class LogRecordExporterHolder {
  private final LogRecordExporter logRecordExporter;
  private final boolean isBatchingEnabled;
  private Resource resource;

  public LogRecordExporterHolder(boolean isBatchingEnabled, LogRecordExporter logRecordExporter) {
    this.isBatchingEnabled = isBatchingEnabled;
    this.logRecordExporter = logRecordExporter;
  }

  public LogRecordExporter getLogRecordExporter() {
    return logRecordExporter;
  }

  public boolean isBatchingEnabled() {
    return isBatchingEnabled;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  @Override
  public String toString() {
    return "LogRecordExporterHolder{"
        + "logRecordExporter="
        + logRecordExporter
        + ", isBatchingEnabled="
        + isBatchingEnabled
        + ", resource="
        + resource
        + '}';
  }
}
