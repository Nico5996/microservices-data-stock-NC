package com.inti.formation.shop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.shop.api.repository.StockinitRepository;
import com.inti.formation.shop.api.repository.model.Stockinit;

import reactor.core.publisher.Flux;

@Component
public class StockinitServiceImpl implements StockinitService {
	
	@Autowired
	private StockinitRepository stockinitRepository;
	
	@Override
	public Flux<Stockinit> findByMagasin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Stockinit> findByIdProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}