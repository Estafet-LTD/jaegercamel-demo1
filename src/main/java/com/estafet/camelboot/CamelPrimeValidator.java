package com.estafet.camelboot;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelPrimeValidator extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        String rabbitmqUrl = System.getenv("RABBITMQ_URL");
        from("netty4-http:http://0.0.0.0:8080/isPrime")
                .to("log:foo")
                .process("validateService")
                .to("rabbitmq://" + rabbitmqUrl + "/amq.direct?autoDelete=false");

        from("rabbitmq://" + rabbitmqUrl + "/amq.direct?autoDelete=false")
                .to("log:mq");
    }
}
