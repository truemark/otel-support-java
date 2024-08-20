package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.LogRecordExporterHolder;
import java.util.List;

public interface OtelLoggingExportersRegistry {
  List<LogRecordExporterHolder> getRegisteredLoggingExporters();
}
