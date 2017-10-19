package com.estafet.camelboot.decorator;

import io.opentracing.Span;
import io.opentracing.tag.Tags;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.opentracing.decorators.RabbitmqSpanDecorator;

import java.util.Map;

/**
 * Example of adding more information via decorator see:{{@link RabbitmqSpanDecorator}}
 *
 * Created by ivo.costa@estafet.com on 19/10/2017.
 */
public class MyCamelDecorator extends RabbitmqSpanDecorator {
    @Override
    public void pre(Span span, Exchange exchange, Endpoint endpoint) {
        super.pre(span, exchange, endpoint);

        span.setTag(Tags.MESSAGE_BUS_DESTINATION.getKey(), getDestination(exchange, endpoint));

        String messageId = (String)getMessageId(exchange);

        Map<String, Object> headers = exchange.getIn().getHeaders();
        String header = headers.get("CamelHttpQuery").toString();

        int num = Integer.parseInt(header.substring(header.indexOf("=") + 1));

        span.setTag("num", num);

        if (messageId != null) {
            span.setTag(MESSAGE_BUS_ID, messageId);
        }
    }
}
