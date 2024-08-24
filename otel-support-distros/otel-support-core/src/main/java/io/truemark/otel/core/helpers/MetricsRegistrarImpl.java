// MetricsRegistrarImpl.java
package io.truemark.otel.core.helpers;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.DoubleHistogramBuilder;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.LongHistogramBuilder;
import io.opentelemetry.api.metrics.LongUpDownCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.ObservableDoubleGauge;
import io.opentelemetry.api.metrics.ObservableLongGauge;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class MetricsRegistrarImpl implements MetricsRegistrar {

  private static final Logger logger = Logger.getLogger(MetricsRegistrarImpl.class.getName());
  private static final String COUNTER_NOT_REGISTERED = "Counter with name %s not registered.";
  private static final String UPDOWN_COUNTER_NOT_REGISTERED =
      "UpDownCounter with name %s not registered.";
  private static final String HISTOGRAM_NOT_REGISTERED = "Histogram with name %s not registered.";

  private final Meter meter;
  private final ConcurrentMap<String, LongCounter> counters;
  private final ConcurrentMap<String, LongUpDownCounter> upDownCounters;
  private final ConcurrentMap<String, DoubleHistogram> doubleHistograms;
  private final ConcurrentMap<String, LongHistogram> longHistograms;
  private final ConcurrentMap<String, ObservableLongGauge> longGauges;
  private final ConcurrentMap<String, ObservableDoubleGauge> doubleGauges;

  public MetricsRegistrarImpl(Meter meter) {
    this.meter = meter;
    this.counters = new ConcurrentHashMap<>();
    this.upDownCounters = new ConcurrentHashMap<>();
    this.doubleHistograms = new ConcurrentHashMap<>();
    this.longHistograms = new ConcurrentHashMap<>();
    this.longGauges = new ConcurrentHashMap<>();
    this.doubleGauges = new ConcurrentHashMap<>();
  }

  @Override
  public LongCounter registerCounter(String counterName, String description, String unit) {
    return counters.computeIfAbsent(
        counterName,
        name -> meter.counterBuilder(name).setDescription(description).setUnit(unit).build());
  }

  @Override
  public LongUpDownCounter registerUpDownCounter(
      String counterName, String description, String unit) {
    return upDownCounters.computeIfAbsent(
        counterName,
        name -> meter.upDownCounterBuilder(name).setDescription(description).setUnit(unit).build());
  }

  @Override
  public DoubleHistogram registerDoubleHistogram(
      String histogramName, String description, String unit, List<Double> bucketBoundaries) {
    final DoubleHistogramBuilder builder =
        meter.histogramBuilder(histogramName).setDescription(description).setUnit(unit);
    if (bucketBoundaries != null && !bucketBoundaries.isEmpty()) {
      builder.setExplicitBucketBoundariesAdvice(bucketBoundaries);
    }
    return doubleHistograms.computeIfAbsent(histogramName, name -> builder.build());
  }

  @Override
  public LongHistogram registerLongHistogram(
      String histogramName, String description, String unit, List<Long> bucketBoundaries) {
    final LongHistogramBuilder builder =
        meter.histogramBuilder(histogramName).setDescription(description).setUnit(unit).ofLongs();
    if (bucketBoundaries != null && !bucketBoundaries.isEmpty()) {
      builder.setExplicitBucketBoundariesAdvice(bucketBoundaries);
    }
    return longHistograms.computeIfAbsent(histogramName, name -> builder.build());
  }

  @Override
  public void registerLongGauge(
      String gaugeName, String description, String unit, Supplier<Long> valueSupplier) {
    try (ObservableLongGauge gauge =
        meter
            .gaugeBuilder(gaugeName)
            .setDescription(description)
            .setUnit(unit)
            .ofLongs()
            .buildWithCallback(
                observableMeasurement -> {
                  long value = valueSupplier.get();
                  observableMeasurement.record(value, Attributes.empty());
                })) {
      longGauges.put(gaugeName, gauge);
    }
  }

  @Override
  public void registerDoubleGauge(
      String gaugeName, String description, String unit, Supplier<Double> valueSupplier) {
    try (ObservableDoubleGauge gauge =
        meter
            .gaugeBuilder(gaugeName)
            .setDescription(description)
            .setUnit(unit)
            .buildWithCallback(
                observableMeasurement -> {
                  double value = valueSupplier.get();
                  observableMeasurement.record(value, Attributes.empty());
                })) {
      doubleGauges.put(gaugeName, gauge);
    }
  }

  @Override
  public void incrementCounter(String counterName, long value, Attributes attributes) {
    LongCounter counter = counters.get(counterName);
    if (counter != null) {
      counter.add(value, attributes);
    } else {
      logger.severe(String.format(COUNTER_NOT_REGISTERED, counterName));
      throw new IllegalStateException(String.format(COUNTER_NOT_REGISTERED, counterName));
    }
  }

  @Override
  public void updateUpDownCounter(String counterName, long value, Attributes attributes) {
    LongUpDownCounter upDownCounter = upDownCounters.get(counterName);
    if (upDownCounter != null) {
      upDownCounter.add(value, attributes);
    } else {
      logger.severe(String.format(UPDOWN_COUNTER_NOT_REGISTERED, counterName));
      throw new IllegalStateException(String.format(UPDOWN_COUNTER_NOT_REGISTERED, counterName));
    }
  }

  @Override
  public void recordHistogram(String histogramName, double value, Attributes attributes) {
    DoubleHistogram histogram = doubleHistograms.get(histogramName);
    if (histogram != null) {
      histogram.record(value, attributes);
    } else {
      logger.severe(String.format(HISTOGRAM_NOT_REGISTERED, histogramName));
      throw new IllegalStateException(String.format(HISTOGRAM_NOT_REGISTERED, histogramName));
    }
  }

  @Override
  public ObservableDoubleGauge getDoubleGauge(String gaugeName) {
    return doubleGauges.get(gaugeName);
  }

  @Override
  public ObservableLongGauge getLongGauge(String gaugeName) {
    return longGauges.get(gaugeName);
  }
}
