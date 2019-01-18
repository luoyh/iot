package com.farpower.iot.client;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farpower.iot.client.model.Message;
import com.farpower.iot.client.model.Session;
import com.farpower.iot.client.util.AssistUtils;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 9, 2019
 * @version 1.0
 * @since 1.8
 */
public class Sender {
    
    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);
    
    private static final String TOPIC = "message.raw";
    
    private static KafkaProducer<byte[], byte[]> producer = null;
    static Properties props = new Properties();
    
    static {
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "iot.producer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop003:9092,hadoop005:9092,hadoop004:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
    }
    
    public synchronized static KafkaProducer<byte[], byte[]> create() {
        if (null == producer) {
            producer = new KafkaProducer<>(props);
        }
        return producer;
    }
    
    public static void send(final Message message) {
        create();
        producer.flush();
        producer.send(new ProducerRecord<byte[], byte[]>(TOPIC, message.getRaw()), (RecordMetadata metadata, Exception exception) -> {
            if (null != exception) {
                exception.printStackTrace();
            } else {
                byte[] con = message.confirmMessage();
                if (con != null) {
                    AssistUtils.printBytes("confirm msg: ", con);
                    Session session = Cache.INSTANCE.get(message.getAddress());
                    if (null != session) {
                        session.getCtx().writeAndFlush(con);
                    } else {
                        LOG.info("session is NULL!");
                    }
                }
            }
        });
    }

}
