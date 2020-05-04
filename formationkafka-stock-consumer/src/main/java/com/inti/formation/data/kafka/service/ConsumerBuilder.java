package com.inti.formation.data.kafka.service;

import com.inti.formation.data.kafka.message.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerBuilder {

	@KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.consumer-group-id}")
	public void consume(Stock stock) {
		log.info("stock readed " + stock.toString() );
	}
}
