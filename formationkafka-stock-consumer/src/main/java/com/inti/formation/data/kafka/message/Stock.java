package com.inti.formation.data.kafka.message;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * 
 * @author Cecile Pieters
 *
 */
@Data

public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private long quantite;
	private String magasin;
	private Boolean active;
	private long idproduct;
	private Date date;

}
