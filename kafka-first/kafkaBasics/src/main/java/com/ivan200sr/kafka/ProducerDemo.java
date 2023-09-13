package com.ivan200sr.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());
    public static void main(String[] args) {
        log.info("Producer working");


        // Create Producer Properties

        Properties properties = new Properties();

        //connecto to localhost
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        //set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());


        // Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //Create a producer Record
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>("demo_java","hello World");

        // send data
        producer.send(producerRecord);

        //Flush (Tell the producer to send all data and block until done (synchronous)) and close the producer
        producer.flush();

        producer.close();



    }
}
