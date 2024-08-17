package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.creators.SdkTracerProviderCreator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class SdkTracerProviderCreatorTest {

    static Stream<Boolean> batchingEnabledProvider() {
        return Stream.of(true, false);
    }

    @ParameterizedTest
    @MethodSource("batchingEnabledProvider")
    public void test_createTracerProvider_givenVaryingInputs(boolean isBatchingEnabled) {
        Resource mockResource = mock(Resource.class);
        SpanExporter mockSpanExporter = mock(SpanExporter.class);

        SdkTracerProvider tracerProvider = SdkTracerProviderCreator.createTracerProvider(
            mockResource, mockSpanExporter, isBatchingEnabled);

        assertNotNull(tracerProvider);
    }
}