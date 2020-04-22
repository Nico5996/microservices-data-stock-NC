package com.inti.formation.shop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.shop.api.repository.StockRepository;
import com.inti.formation.shop.api.repository.model.Stock;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class StockServiceImpl implements IStockService {

	@Autowired
    private StockRepository stockRepository;
	@Override
	public Mono<Stock> register(final Stock stock) {
		return stockRepository.save(stock);
	}

	@Override
	public Flux<Stock> getStock() {
		return stockRepository.findAll();
	}

}
