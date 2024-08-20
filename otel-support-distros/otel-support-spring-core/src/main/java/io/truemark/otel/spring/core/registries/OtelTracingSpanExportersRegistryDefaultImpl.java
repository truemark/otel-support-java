package io.truemark.otel.spring.core.registries;

import io.truemark.otel.core.models.SpanExporterHolder;
import java.util.Collections;
import java.util.List;

public class OtelTracingSpanExportersRegistryDefaultImpl
    implements OtelTracingSpanExportersRegistry {
  @Override
  public List<SpanExporterHolder> getRegisterSpanExporterHolders() {
    return Collections.emptyList();
  }
}
