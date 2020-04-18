package com.inti.formation.shop.api;



import com.inti.formation.shop.api.repository.IProductRepository;
import com.inti.formation.shop.api.repository.IStockInitRepository;
import com.inti.formation.shop.api.repository.model.Customer;
import com.inti.formation.shop.api.repository.model.Product;
import com.inti.formation.shop.api.repository.model.StockInit;
import com.inti.formation.shop.api.rest.exception.InternalServerException;
import com.inti.formation.shop.api.rest.exception.ValidationParameterException;
import com.inti.formation.shop.api.service.CustomerService;
import com.inti.formation.shop.api.service.IProductService;
import com.inti.formation.shop.api.service.IStockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;



@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/shop")
@Slf4j
public class Endpoint {
    @Autowired
    CustomerService customerService;


	@Autowired
	IProductService productservice;
	
	@Autowired
	IStockService stockinitservice;
    
    
    @ExceptionHandler(ValidationParameterException.class)
    public Mono<ResponseEntity<String>> handlerValidationParameterException(ValidationParameterException e) {
        return Mono.just(badRequest().body("Missing parameter: "+ e.getMessage()));
    }

    @ExceptionHandler(InternalServerException.class)
    public Mono<ResponseEntity<String>> handlerInternalServerException() {
        return Mono.just(status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error server has occurred "));
    }

    //Requete POST --------------------------------------
    
    @PostMapping(value = "/register" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Customer is registered" )
    public Mono<String> create(@RequestBody Customer customer) {

        if( ObjectUtils.anyNotNull(customer)  && !ObjectUtils.allNotNull(customer.getEmail(),customer.getName(), customer.getFirstname() )){
            log.error("Validation error: one of parameter is not found");
            return Mono.error(new ValidationParameterException("Validation error" ));
        }
        return Mono.just(customer)
                .map(data->
                {
                     return customerService.register( data).subscribe().toString();
                });
    }

    
    @PostMapping(value = "/register" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Product is registered" )
    public Mono<String> create(@RequestBody Product product) {
		
    	 if( ObjectUtils.anyNotNull(product)  && !ObjectUtils.allNotNull(product.getId(),product.getLibelle(),product.getOrigine(), product.getCouleur() )){
             log.error("Validation error: one of parameter is not found");
             return Mono.error(new ValidationParameterException("Validation error" ));
         }
    	 return Mono.just(product)
                 .map(data-> {return productservice.register( data).subscribe().toString();
                 });
    }
    
    
    
    @PostMapping(value = "/register" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Stock is registered" )
    public Mono<String> create(@RequestBody StockInit stockInit) {
		
    	 if( ObjectUtils.anyNotNull(stockInit)  && !ObjectUtils.allNotNull(stockInit.getId(), stockInit.getMagasin(), stockInit.getQuantite(), stockInit.getIdproduct(), stockInit.getDate() )){
             log.error("Validation error: one of parameter is not found");
             return Mono.error(new ValidationParameterException("Validation error" ));
         }
    	 return Mono.just(stockInit)
                 .map(data-> {return stockinitservice.register( data).subscribe().toString();
                 });
    }
    
    
    
    
    
    // Requete GET --------------------------------------
    
    
    
    

    @GetMapping
    @RequestMapping(value = "/customers{customername}")
    public Flux<Customer> getCustomes(@RequestParam(required = true, name = "customername") String customername ) {
        log.info("Searching  {} ",customername );
        return customerService.searchName(customername)
                // uses of doNext
                .doOnNext(p -> log.info(p.getEmail()+ " is found"));

    }


    @GetMapping
    @RequestMapping(value = "/customers/")
    public Flux<Customer> getCustomes() {
        log.info("All customers searching");
      return customerService.getCustomers()
              // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( customer-> customer);
    }
}

