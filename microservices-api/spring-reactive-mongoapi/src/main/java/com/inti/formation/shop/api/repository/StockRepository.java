package com.inti.formation.shop.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.inti.formation.shop.api.repository.model.Stock;

/**
 * 
 * @author Cecile Pieters
 *
 */

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, Long>{
	
//	/**
//	 *
//	 * @param magasin
//	 * @return Stockinit with parameter magasin
//	 */
//	Flux<Stockinit> findByMagasin(String magasin);
//
//	/**
//	 *
//	 * @param idproduct
//	 * @return Stockinit with parameter idproduct
//	 */
//	Flux<Stockinit> findByIdproduct(long idproduct);
//	
//	
//	@Query(value="{"
//	            + "'magasin': {$elemMatch: {'idproduct': ?0}}, ")
//	Flux<Stockinit> findByIdproductANDByMagasin(final String magasin, final long idproduct);
	
}
