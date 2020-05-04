package com.inti.formation.shop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.shop.api.repository.StockRepository;
import com.inti.formation.shop.api.repository.model.Stock;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Cecile Pieters
 *
 */

@Component
@Slf4j
public class StockServiceImpl implements IStockService {

	@Autowired
	private StockRepository stockRepository;

	@Override
	public Mono<Stock> register(Stock stock) {
		
		return stockRepository.save(stock);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return stockRepository.deleteById(id);
		
	}

	@Override
	public Mono<Stock> findById(String id) {
		return stockRepository.findById(id);
	}

}
