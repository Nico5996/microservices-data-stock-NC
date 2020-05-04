package com.inti.formation.data.kafka.producer.producer;

import java.util.Date;
import java.util.Random;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.inti.formation.shop.api.repository.model.Stock;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sylvanius Kouandongui
 *
 */
@Slf4j
@Component
public class ProducerBuilderStock {

	@Value("${kafka.topic-name}")
	private String StockTopic;

	@Autowired
	private KafkaTemplate<String, Stock> kafkaTemplate;

	@Value("${kafka.compression-type}")
	private String compressionType;

	// milliseconds
	@Scheduled(fixedDelayString = "${schedule-time}")
	public void scheduleFixedDelayTask(KafkaTemplate<String, Stock> kafka) {
		Integer defectNumber = new Random().ints(1, (1000 + 1)).findFirst().getAsInt();
		Stock stock = new Stock();
		stock.setId(defectNumber.toString());
		stock.setMagasin(defectNumber.toString());
//		stock.setQuantite(defectNumber);
//		stock.setIdproduct(defectNumber);
//		stock.setDate(new Date());
//		stock.setActive(true);
		
		
		ProducerRecord<String, Stock> producerRecord = new ProducerRecord<>("delete-product-v1", stock.getId(),stock);
		kafka.send(producerRecord);
	}

}
