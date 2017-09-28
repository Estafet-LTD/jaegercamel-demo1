package com.estafet.camelboot;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

@Service
public class ValidateService implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String header = exchange.getIn().getHeader("CamelHttpQuery").toString();
        int num = Integer.parseInt(header.substring(header.indexOf("=")+1));

        boolean prime = num>1;

        for(int x = 2; x < num && prime ; x++) {
            prime = (num%x) != 0;
        }
        exchange.getOut().setBody("{\"result\": " + prime + "}");
    }
}
