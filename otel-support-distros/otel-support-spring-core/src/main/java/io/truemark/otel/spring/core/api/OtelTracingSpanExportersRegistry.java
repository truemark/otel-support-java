package io.truemark.otel.spring.core.api;

import io.truemark.otel.core.models.SpanExporterHolder;
import java.util.Collections;
import java.util.List;

public interface OtelTracingSpanExportersRegistry {

  default List<SpanExporterHolder> getRegisterSpanExporterHolders() {
    return Collections.emptyList();
  }
}
