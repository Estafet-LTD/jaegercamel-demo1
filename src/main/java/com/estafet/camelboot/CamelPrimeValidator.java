package com.estafet.camelboot;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelPrimeValidator extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("netty4-http:http://0.0.0.0:8080/isPrime")
                .process("validateService");
    }
}
