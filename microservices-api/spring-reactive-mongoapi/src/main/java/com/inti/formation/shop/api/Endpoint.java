package com.inti.formation.shop.api;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inti.formation.data.kafka.producer.producer.ProducerBuilderStock;
import com.inti.formation.shop.api.rest.bean.StockRequest;
import com.inti.formation.shop.api.rest.exception.InternalServerException;
import com.inti.formation.shop.api.rest.exception.ValidationParameterException;
import com.inti.formation.shop.api.service.IStockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/stock") // toutes mes api vont commencer par V1shop
@Slf4j

//Controller, Root

public class Endpoint {

	@Autowired
	IStockService stockService;
	@Autowired
	KafkaTemplate kafkaTemplate;
	
	// Exception par ex: champ obligatoire
	@ExceptionHandler(ValidationParameterException.class)
	public Mono<ResponseEntity<String>> handlerValidationParameterException(ValidationParameterException e) {
		return Mono.just(badRequest().body("Missing parameter: " + e.getMessage()));
	}

	@ExceptionHandler(InternalServerException.class)
	public Mono<ResponseEntity<String>> handlerInternalServerException() {
		return Mono.just(status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error server has occurred "));
	}

	@PostMapping(value = "/register/stock", headers = "Accept=application/json; charset=utf-8") /*
																								 * headers = type de la
																								 * réponse
																								 */
	@ResponseStatus(value = HttpStatus.CREATED, reason = "Product is registered") // message pour l'utilisateur (ne
																					// change pas ce qui se passe ds
																					// base de donnée)
	public Mono<String> create(@RequestBody StockRequest stock) {

		if (ObjectUtils.anyNotNull(stock) && !ObjectUtils.allNotNull(stock.getId(), stock.getMagasin())) {
			log.error("Validation error: one of parameter is not found");
			return Mono.error(new ValidationParameterException("Validation error"));
		}
		return Mono.just(stock).map(data -> {
			return stockService.register(data).subscribe().toString();
		});
	}
	
	@DeleteMapping("/delete/stock/{id}")
	public Mono<Void> delete(@PathVariable String id) {
		ProducerBuilderStock newStock = new ProducerBuilderStock();
		newStock.scheduleFixedDelayTask(kafkaTemplate);
		  return stockService.deleteById(id);
	    }

}
