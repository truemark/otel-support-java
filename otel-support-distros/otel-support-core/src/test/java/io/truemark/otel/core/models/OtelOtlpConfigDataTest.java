package io.truemark.otel.core.models;

import io.truemark.otel.core.models.OtelOtlpConfigData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class OtelOtlpConfigDataTest {

    @ParameterizedTest
    @CsvSource({
        "true, http://localhost:4317",
        "false, ",
        "false, http://localhost:4317"
    })
    public void test_OtelOtlpConfigDataConstructorAndGetters_givenValidInputs(boolean otlpEnabled, String otlpEndpoint) {
        OtelOtlpConfigData otlpConfig = new OtelOtlpConfigData(otlpEnabled, otlpEndpoint);
        assertNotNull(otlpConfig.toString());
        assertEquals(otlpEnabled, otlpConfig.isOtlpEnabled());
        assertEquals(otlpEndpoint, otlpConfig.getOtlpEndpoint());
    }

    @ParameterizedTest
    @CsvSource({
        "true, ",
        "true, ''"
    })
    public void test_OtelOtlpConfigDataConstructor_givenInvalidInputs(boolean otlpEnabled, String otlpEndpoint) {
        assertThrows(IllegalArgumentException.class, () -> new OtelOtlpConfigData(otlpEnabled, otlpEndpoint));
    }
}