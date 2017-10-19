package com.estafet.camelboot;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.opentracing.ActiveSpanManager;
import org.apache.camel.opentracing.propagation.CamelHeadersExtractAdapter;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Example of creating a span manually
 */
@Service
public class ValidateService implements Processor {
    @Autowired
    private Tracer tracer;

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();
        Map<String, Object> headers = message.getHeaders();
        String header = headers.get("CamelHttpQuery").toString();

        int num = Integer.parseInt(header.substring(header.indexOf("=") + 1));

        Span parentSpan = ActiveSpanManager.getSpan(exchange);
        Span span = tracer.buildSpan("ValidatePrime").asChildOf(parentSpan).withTag("number", num).startManual();

        boolean prime = num > 1;

        for (int x = 2; x < num && prime; x++) {
            prime = (num % x) != 0;
        }

        span.setTag("result", prime);

        exchange.getOut().setBody("{\"result\": " + prime + "}");

        span.finish();
    }
}
