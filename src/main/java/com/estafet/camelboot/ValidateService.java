package com.estafet.camelboot;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidateService implements Processor {
    @Autowired
    private Tracer tracer;

    @Override
    public void process(Exchange exchange) throws Exception {
        String header = exchange.getIn().getHeader("CamelHttpQuery").toString();
        int num = Integer.parseInt(header.substring(header.indexOf("=")+1));

        Map<String, Object> headers = exchange.getIn().getHeaders();

        SpanContext spanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new ObjectMapExtracterAdapter(headers));

        Span span = tracer.buildSpan("ValidatePrime").asChildOf(spanContext).withTag("number", num).startManual();

        boolean prime = num>1;

        for(int x = 2; x < num && prime ; x++) {
            prime = (num%x) != 0;
        }
        exchange.getOut().setBody("{\"result\": " + prime + "}");

        span.finish();
    }
}
