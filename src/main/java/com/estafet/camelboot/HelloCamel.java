package com.estafet.camelboot;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloCamel extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        from("timer://foo?period=10000")
//                .to("log:bar");

        MyRandomizer myRandomizer = new MyRandomizer();
        from("netty4-http:http://0.0.0.0:8080/foo")
                .delay().method(myRandomizer, "nextRandom")
                .transform().constant("Greetings World");
    }
}
