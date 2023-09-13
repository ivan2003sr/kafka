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

public class ProducerDemoKeys {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getSimpleName());
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

for (int j=0 ; j<5; j++){
            for (int i = 0; i < 10; i++) {
                String topic = "demo_java";
                String key = "id_" + i;
                String value = "Holañ gente " + i;
                //Create a producer Record
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

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
                            log.info("Key: " + key + " | Partition: " + recordMetadata.partition()
                            );
                        } else {
                            log.error("Error while producing ", e);
                        }
                    }
                });
            }
            }

        //Flush (Tell the producer to send all data and block until done (synchronous)) and close the producer
            producer.flush();

            producer.close();

    }


}
