package com.sparrows.board.kafka.config;


//카프카 메시지 여러개를 하나의 트랜잭션으로 묶어서 정합성 보장하고 싶다면 써라
//@EnableTransactionManagement
//@Configuration
//public class KafkaTransactionConfig {
//    @Bean
//    public KafkaTransactionManager<String, String> kafkaTransactionManager(
//            ProducerFactory<String, String> producerFactory) {
//        return new KafkaTransactionManager<>(producerFactory);
//    }
//}
