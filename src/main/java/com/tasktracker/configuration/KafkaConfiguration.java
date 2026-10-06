package com.tasktracker.configuration;


import com.tasktracker.kafka.rpc.summarization.SchedulerSummarizationRequest;
import com.tasktracker.kafka.rpc.summarization.SchedulerSummarizationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final Environment environment;


    @Bean
    ConcurrentKafkaListenerContainerFactory<UUID, SchedulerSummarizationRequest> kafkaListenerContainerFactory(
            ConsumerFactory<UUID, SchedulerSummarizationRequest> consumerFactory,
            KafkaTemplate<UUID, SchedulerSummarizationResponse> replyTemplate) {

        ConcurrentKafkaListenerContainerFactory<UUID, SchedulerSummarizationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L)));
        factory.setReplyTemplate(replyTemplate);

        return factory;
    }

    @Bean
    ProducerFactory<UUID, SchedulerSummarizationResponse> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    KafkaTemplate<UUID, SchedulerSummarizationResponse> replyTemplate(
            ProducerFactory<UUID, SchedulerSummarizationResponse> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }


    @Bean
    ConsumerFactory<UUID, SchedulerSummarizationRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig());
    }


    private Map<String, Object> getConsumerConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, environment.getProperty("spring.kafka.consumer.trusted-packages"));
        config.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, SchedulerSummarizationRequest.class);

        return config;
    }

}