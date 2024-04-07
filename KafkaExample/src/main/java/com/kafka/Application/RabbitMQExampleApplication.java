package com.kafka.Application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.kafka.consumer.RabbitMQConsumer;
import com.kafka.producer.RabbitMQProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ComponentScan({"com.kafka"}) // Scan all packages under com.kafka

public class RabbitMQExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RabbitMQExampleApplication.class, args);

        RabbitMQProducer producer = context.getBean(RabbitMQProducer.class);
        RabbitMQConsumer consumer = context.getBean(RabbitMQConsumer.class);

        producer.sendMessage("Hello RabbitMQ!");


    }
}
