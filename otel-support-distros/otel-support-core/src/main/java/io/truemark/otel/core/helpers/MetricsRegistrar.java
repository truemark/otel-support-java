package io.truemark.otel.core.helpers;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.LongUpDownCounter;
import io.opentelemetry.api.metrics.ObservableDoubleGauge;
import io.opentelemetry.api.metrics.ObservableLongGauge;

import java.util.List;
import java.util.function.Supplier;

public interface MetricsRegistrar {
    LongCounter registerCounter(String counterName, String description, String unit);

    LongUpDownCounter registerUpDownCounter(String counterName, String description, String unit);

    DoubleHistogram registerDoubleHistogram(String histogramName, String description, String unit, List<Double> bucketBoundaries);

    LongHistogram registerLongHistogram(String histogramName, String description, String unit, List<Long> bucketBoundaries);

    void registerLongGauge(String gaugeName, String description, String unit, Supplier<Long> valueSupplier);

    void registerDoubleGauge(String gaugeName, String description, String unit, Supplier<Double> valueSupplier);

    void incrementCounter(String counterName, long value, Attributes attributes);

    void updateUpDownCounter(String counterName, long value, Attributes attributes);

    void recordHistogram(String histogramName, double value, Attributes attributes);

    ObservableDoubleGauge getDoubleGauge(String gaugeName);

    ObservableLongGauge getLongGauge(String gaugeName);
}
