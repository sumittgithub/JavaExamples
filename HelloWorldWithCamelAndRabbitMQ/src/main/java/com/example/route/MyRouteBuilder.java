package com.example.route;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:queue:helloQueue")
            .log("Received message from ActiveMQ: ${body}");
    }
}
