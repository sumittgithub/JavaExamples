package com.example.route;


import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping("/submit")
    public String submitMessage(@RequestParam String message) {
        producerTemplate.sendBody("activemq:queue:helloQueue", message);
        return "Message sent to ActiveMQ: " + message;
    }
}
