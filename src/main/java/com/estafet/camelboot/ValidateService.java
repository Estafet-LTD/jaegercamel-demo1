package com.estafet.camelboot;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidateService implements Processor {
    @Autowired
    private Tracer tracer;

    private static Logger log = Logger.getLogger(ValidateService.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("processor start");
        Message message = exchange.getIn();
        Map<String, Object> headers = message.getHeaders();
        String header = headers.get("CamelHttpQuery").toString();

        log.debug("debugging");
        int num = Integer.parseInt(header.substring(header.indexOf("=") + 1));

        SpanContext spanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new ObjectMapExtractAdapter(headers));
        Span span = tracer.buildSpan("ValidatePrime").asChildOf(spanContext).withTag("number", num).startManual();

        log.error("error faked");
        boolean prime = num > 1;

        for (int x = 2; x < num && prime; x++) {
            prime = (num % x) != 0;
        }
        exchange.getOut().setBody("{\"result\": " + prime + "}");

        log.info("finished");
        span.finish();
    }
}
