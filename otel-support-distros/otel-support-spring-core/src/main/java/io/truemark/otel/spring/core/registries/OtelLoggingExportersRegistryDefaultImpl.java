package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.LogRecordExporterHolder;
import java.util.Collections;
import java.util.List;

public class OtelLoggingExportersRegistryDefaultImpl implements OtelLoggingExportersRegistry {

  @Override
  public List<LogRecordExporterHolder> getRegisteredLoggingExporters() {
    return Collections.emptyList();
  }
}
