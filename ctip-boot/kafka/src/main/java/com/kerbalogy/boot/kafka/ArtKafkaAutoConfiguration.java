package com.kerbalogy.boot.kafka;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-04
 * @description
 **/
@Configuration
@EnableConfigurationProperties(ArtKafkaProperties.class)
public class ArtKafkaAutoConfiguration implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // 解析配置文件，读取到ArtKafkaProperties中去
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)beanDefinitionRegistry;
        Environment environment = beanFactory.getBean(Environment.class);
        ArtKafkaProperties defaultProperties = new ArtKafkaProperties();
        defaultProperties.getConfig().putIfAbsent(defaultProperties.getDefaultConfig(), new KafkaProperties());
        ArtKafkaProperties artKafkaProperties = Binder.get(environment).bind("art.spring.kafka", ArtKafkaProperties.class).orElse(defaultProperties);
        // 添加AOP日志
        beanDefinitionRegistry.registerBeanDefinition("consumerErrorHandler", new RootBeanDefinition(ConsumerErrorHandler.class));

        // 为配置文件中每个配置创建一个bean
        artKafkaProperties.getConfig().forEach(
                (kafkaMark, kafkaProperties) -> {
                    // 从配置文件创建Bean
                    // new ProducerFactory(Map congis)
                    BeanDefinition kafkaProducerFactoryBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultKafkaProducerFactory.class)
                            .addConstructorArgValue(kafkaProperties.buildProducerProperties())
                            .getBeanDefinition();
                    // new ConsumerFactory(Map configs)
                    BeanDefinition kafkaConsumerFactoryBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultKafkaConsumerFactory.class)
                            .addConstructorArgValue(kafkaProperties.buildConsumerProperties())
                            .getBeanDefinition();
                    // new KafkaTemplate(ProducerFactory)
                    BeanDefinition kafkaTemplateBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(KafkaTemplate.class)
                            .addConstructorArgReference(KafkaBeanNameUtil.getProducerFactoryBeanName(kafkaMark))
                            .getBeanDefinition();
                    // 以上都没问题

                    // ConcurrentKafkaListenerContainerFactory.setConsumerFactory( consumerFactory )
                    BeanDefinition kafkaListenerContainerFactoryBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ConcurrentKafkaListenerContainerFactory.class)
                            .addPropertyReference("consumerFactory", KafkaBeanNameUtil.getConsumerFactoryBeanName(kafkaMark))
                            .getBeanDefinition();

                    // 自定义的KafkaProducer，而不是org.apache.kafka.clients.producer.KafkaProducer;

                    BeanDefinition kafkaProducerBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(KafkaProducer.class)
                            .addPropertyReference("kafkaTemplate", KafkaBeanNameUtil.getKafkaTemplateBeanName(kafkaMark))
                            .getBeanDefinition();

                    // 设置默认配置，设置primary
                    boolean isDefaultConfig = artKafkaProperties.getDefaultConfig().equals(kafkaMark);
                    if (isDefaultConfig) {
                        kafkaProducerFactoryBeanDefinition.setPrimary(true);
                        kafkaConsumerFactoryBeanDefinition.setPrimary(true);
                        kafkaTemplateBeanDefinition.setPrimary(true);
                        kafkaProducerBeanDefinition.setPrimary(true);
                        kafkaListenerContainerFactoryBeanDefinition.setPrimary(true);
                    }

                    // beanName，BeanDefinition，存入
                    beanDefinitionRegistry.registerBeanDefinition(KafkaBeanNameUtil.getProducerFactoryBeanName(kafkaMark), kafkaProducerFactoryBeanDefinition);
                    beanDefinitionRegistry.registerBeanDefinition(KafkaBeanNameUtil.getConsumerFactoryBeanName(kafkaMark), kafkaConsumerFactoryBeanDefinition);
                    beanDefinitionRegistry.registerBeanDefinition(KafkaBeanNameUtil.getKafkaTemplateBeanName(kafkaMark), kafkaTemplateBeanDefinition);
                    beanDefinitionRegistry.registerBeanDefinition(KafkaBeanNameUtil.getKafkaListenerContainerBeanName(kafkaMark), kafkaListenerContainerFactoryBeanDefinition);
                    beanDefinitionRegistry.registerBeanDefinition(KafkaBeanNameUtil.getKafkaProducerBeanName(kafkaMark), kafkaProducerBeanDefinition);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
