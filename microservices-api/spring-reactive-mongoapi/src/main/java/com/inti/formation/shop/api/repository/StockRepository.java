package com.inti.formation.shop.api.repository;

import java.sql.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Flux;

/**
 * 
 * @author Cecile Pieters
 *
 */

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, Long> {

	/**
	 * 
	 * @param active
	 * @param date
	 * @return liste stock par paramètres active,date
	 */

	@Query("{'$and':[ {'boolean':?0}, {'date':?1} ] }")
	Flux<Stock> searchStockActiveFromDate(final boolean active, final Date date);

	/**
	 *
	 * @param magasin
	 * @return Stockinit with parameter magasin
	 */
	Flux<Stock> findByMagasin(String magasin);

	void deleteById(String id);

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
