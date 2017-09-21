package com.estafet.camelboot;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloCamel extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:foo")
                .to("log:bar");
    }
}
