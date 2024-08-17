package io.truemark.otel.core.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OtelTracingConfigDataTest {

    @ParameterizedTest
    @CsvSource({
        "true, true",
        "true, false",
        "false, true",
        "false, false"
    })
    public void test_OtelTracingConfigDataConstructorAndGetters_givenVaryingInputs(boolean tracingEnabled, boolean isBatchingEnabled) {
        OtelTracingConfigData tracingConfig = new OtelTracingConfigData(tracingEnabled, isBatchingEnabled);
        assertNotNull(tracingConfig.toString());
        assertEquals(tracingEnabled, tracingConfig.isTracingEnabled());
        assertEquals(isBatchingEnabled, tracingConfig.isBatchingEnabled());
    }

}