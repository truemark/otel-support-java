package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.SpanExporterHolder;
import java.util.List;

public interface OtelTracingSpanExportersRegistry {

  List<SpanExporterHolder> getRegisterSpanExporterHolders();
}
