package io.truemark.otel.spring.core.registries;

import io.opentelemetry.sdk.trace.samplers.Sampler;

public class OtelTracingSamplerRegistryDefaultImpl implements OtelTracingSamplerRegistry {

  @Override
  public Sampler getRegisteredSampler() {
    return null;
  }
}
