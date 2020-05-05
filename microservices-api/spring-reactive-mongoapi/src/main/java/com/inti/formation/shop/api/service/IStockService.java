package com.inti.formation.shop.api.service;

import org.springframework.http.ResponseEntity;

import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStockService {

	Mono<Stock> register (final Stock stock);
	
	Mono<Stock> findById (final String id);
	
	public Mono<Void> deleteById(final String id);
	
	Flux<Stock> findByActive(final boolean active);
	
	Flux<Stock> findByMagasin(final String magasin);

	Mono<Stock> update(final Stock stock);
	
}
