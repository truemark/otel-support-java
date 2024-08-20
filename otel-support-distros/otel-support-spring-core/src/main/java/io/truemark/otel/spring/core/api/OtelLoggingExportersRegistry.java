package io.truemark.otel.spring.core.api;

import io.truemark.otel.core.models.LogRecordExporterHolder;
import java.util.List;

public interface OtelLoggingExportersRegistry {
  List<LogRecordExporterHolder> getRegisteredLoggingExporters();
}
