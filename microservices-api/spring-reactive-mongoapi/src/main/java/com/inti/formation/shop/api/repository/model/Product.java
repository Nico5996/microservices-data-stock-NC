package com.inti.formation.shop.api.repository.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author Cecile Pieters
 *
 */

@Data
@Document(collection = "product")
@CompoundIndexes({
      //  @CompoundIndex(name = "product", def = "{ libelle: 1}", unique = false)
        // unique = false acceptation des doublons true non
        // unique = true rejet  des doublons
})

public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private Long id;
    //@Indexed(unique = false)
    private String libelle;
    private String origine;
    private String description;
    private List<String> couleur;
    
}
