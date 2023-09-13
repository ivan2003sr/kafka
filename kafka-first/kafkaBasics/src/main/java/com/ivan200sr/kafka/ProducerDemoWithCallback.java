package com.ivan200sr.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ProducerDemoWithCallback {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getSimpleName());
    public static void main(String[] args) {
        log.info("Producer working");


        // Create Producer Properties

        Properties properties = new Properties();

        //connecto to localhost
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        //set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());

        //Batch size
        properties.setProperty("batch.size","400");

        // Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int j=0 ; j<100; j++) {
            for (int i = 0; i < 100; i++) {
                //Create a producer Record
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "hello World N° " + i);

                // send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        //Format date

                        // executed every time a record is successfully sent or an exception is thrown.
                        if (e == null) {
                            long timestamp = recordMetadata.timestamp();
                            Date date = new Date(timestamp);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String formattedDate = dateFormat.format(date);
                            log.info("Received new metadata \n" + "Topic:" + recordMetadata.topic() + "\n" +
                                    "Timestamp: " + recordMetadata.timestamp() + "\n" +
                                    "Date:" + formattedDate + "\n" +
                                    "Partition:" + recordMetadata.partition() + "\n" +
                                    "Offset:" + recordMetadata.offset() + "\n"
                            );
                        } else {
                            log.error("Error while producing ", e);
                        }
                    }
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //Flush (Tell the producer to send all data and block until done (synchronous)) and close the producer
            producer.flush();

            producer.close();

    }


}
