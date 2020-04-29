package com.inti.formation.shop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.shop.api.repository.StockRepository;
import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Mono;

@Component

public class StockServiceImpl implements IStockService {

	@Autowired
	private StockRepository stockRepository;

	@Override
	public Mono<Stock> register(Stock stock) {
		
		return stockRepository.save(stock);
	}

	@Override
	public void deleteById(String id) {
		stockRepository.deleteById(id);
		
	}

	

}
