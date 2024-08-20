package io.truemark.otel.support.sample.controllers;

import io.opentelemetry.api.OpenTelemetry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController2 {

    private final OpenTelemetry openTelemetry;

    public TestController2(final OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @GetMapping("/test")
    public String test() {
        return "It Works!";
    }
}
