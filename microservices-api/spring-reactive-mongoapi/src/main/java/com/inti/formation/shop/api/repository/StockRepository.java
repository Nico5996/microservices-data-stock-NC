package com.inti.formation.shop.api.repository;

import java.sql.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Cecile Pieters
 *
 */

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, Long> {

	Mono<Void> deleteById(String id);

	Mono<Stock> findById(String id);
	
	Flux<Stock> findByActive(Boolean active);
	
	Flux<Stock> findByMagasin(String magasin);

}
