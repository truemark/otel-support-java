package io.truemark.otel.spring.core.api;

import io.opentelemetry.sdk.trace.samplers.Sampler;

public interface OtelTracingSamplerRegistry {
  Sampler getRegisteredSampler();
}
