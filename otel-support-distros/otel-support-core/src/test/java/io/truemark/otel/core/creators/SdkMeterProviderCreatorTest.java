package io.truemark.otel.core.creators;

import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.creators.SdkMeterProviderCreator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class SdkMeterProviderCreatorTest {

    static Stream<MetricExporter> metricExporterProvider() {
        return Stream.of(LoggingMetricExporter.create());
    }

    @ParameterizedTest
    @MethodSource("metricExporterProvider")
    public void test_createMeterProvider_givenVaryingInputs(MetricExporter metricExporter) {
        Resource mockResource = mock(Resource.class);

        SdkMeterProvider meterProvider = SdkMeterProviderCreator.createMeterProvider(
            mockResource, metricExporter);

        assertNotNull(meterProvider);
    }
}