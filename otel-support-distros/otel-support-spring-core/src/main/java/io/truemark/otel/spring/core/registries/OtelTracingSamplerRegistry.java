package io.truemark.otel.spring.core.registries;

import io.opentelemetry.sdk.trace.samplers.Sampler;

public interface OtelTracingSamplerRegistry {
  Sampler getRegisteredSampler();
}
