package com.inti.formation.shop.api.repository.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author Cecile Pieters
 *
 */
@Data
@Document(collection = "stock")
//@CompoundIndexes({
// @CompoundIndex(name = "stockinit", def = "{ magasin: 1 }", unique = false)
// unique = false acceptation des doublons true non
// unique = true rejet des doublons
//})

public class Stock implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	// @Indexed(unique = false)
	private long quantite;
	private String magasin;
	private Boolean active;
	private long idproduct;
	private Date date;

}
