package com.kerbalogy.boot.kafka;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-04
 * @description
 **/
public class KafkaBeanNameUtil {
    public static final String KAFKA_TEMPLATE_BEAN_NAME_SUFFIX = "KafkaTemplate";
    public static final String PRODUCER_FACTORY_BEAN_NAME_SUFFIX = "ProducerFactory";
    public static final String KAFKA_PRODUCER_BEAN_NAME_SUFFIX = "KafkaProducer";
    public static final String CONSUMER_FACTORY_BEAN_NAME_SUFFIX = "ConsumerFactory";
    public static final String KAFKA_LISTENER_CONTAINER_BEAN_NAME_SUFFIX = "ContainerFactory";

    public static String concat(String mark, String next) {
        if (mark == null || mark.isEmpty()) {
            return Character.toLowerCase(next.charAt(0)) + next.substring(1);
        }
        return mark + next;
    }

    public static String getKafkaTemplateBeanName(String kafkaMark) {
        return concat(kafkaMark, KAFKA_TEMPLATE_BEAN_NAME_SUFFIX);
    }

    public static String getProducerFactoryBeanName(String kafkaMark) {
        return concat(kafkaMark, PRODUCER_FACTORY_BEAN_NAME_SUFFIX);
    }

    public static String getKafkaProducerBeanName(String kafkaMark) {
        return concat(kafkaMark, KAFKA_PRODUCER_BEAN_NAME_SUFFIX);
    }

    public static String getConsumerFactoryBeanName(String kafkaMark) {
        return concat(kafkaMark, CONSUMER_FACTORY_BEAN_NAME_SUFFIX);
    }

    public static String getKafkaListenerContainerBeanName(String kafkaMark) {
        return concat(kafkaMark, KAFKA_LISTENER_CONTAINER_BEAN_NAME_SUFFIX);
    }
}
