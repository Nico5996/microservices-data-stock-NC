package com.inti.formation.shop.api;



import com.inti.formation.shop.api.repository.model.Customer;

import com.inti.formation.shop.api.repository.model.Product;
import com.inti.formation.shop.api.repository.model.Stockinit;
import com.inti.formation.shop.api.rest.bean.CustomerRequest;
import com.inti.formation.shop.api.rest.bean.ProductRequest;
import com.inti.formation.shop.api.rest.bean.StockinitRequest;

import com.inti.formation.shop.api.rest.exception.InternalServerException;
import com.inti.formation.shop.api.rest.exception.ValidationParameterException;
import com.inti.formation.shop.api.service.CustomerService;
import com.inti.formation.shop.api.service.IProductService;
import com.inti.formation.shop.api.service.IStockinitService;

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
@RequestMapping(value = "/v1/shop") //toutes mes api vont commencer par V1shop
@Slf4j

//Controller, Root

public class Endpoint {
    @Autowired
    CustomerService customerService;
    @Autowired
    IProductService productService;
    @Autowired
    IStockinitService stockinitService;

    //Exception par ex: champ oblgatoire
    @ExceptionHandler(ValidationParameterException.class)
    public Mono<ResponseEntity<String>> handlerValidationParameterException(ValidationParameterException e) {
     return Mono.just(
                badRequest().body("Missing parameter: "+ e.getMessage()));
    }

    @ExceptionHandler(InternalServerException.class)
    public Mono<ResponseEntity<String>> handlerInternalServerException() {
        return Mono.just(status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error server has occurred "));
    }


    @PostMapping(value = "/register/customer" , headers = "Accept=application/json; charset=utf-8") /*headers = type de la réponse*/
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Customer is registered" ) // message pour l'utilisateur (ne change pas ce qui se passe ds base de donnée)

    public Mono<String> create(@RequestBody CustomerRequest customer) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(customer)  && !ObjectUtils.allNotNull(customer.getEmail(),customer.getName(), customer.getFirstname() )){
            log.error("Validation error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Validation error message): one of attributes is not found" ));
        }
        return Mono.just(customer)
        .map(data->
                {

                    return customerService.register( data).subscribe().toString();

                });
    }
    
    @PostMapping(value = "/register/product" , headers = "Accept=application/json; charset=utf-8") /*headers = type de la réponse*/
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Product is registered" ) // message pour l'utilisateur (ne change pas ce qui se passe ds base de donnée)
    public Mono<String> create(@RequestBody ProductRequest product) {


        if( ObjectUtils.anyNotNull(product)  && !ObjectUtils.allNotNull(product.getId(),product.getLibelle() )){
            log.error("Validation error: one of parameter is not found");
            return Mono.error(new ValidationParameterException("Validation error" ));
        }
        return Mono.just(product)
                .map(data->
                {
                     return productService.register(data).subscribe().toString();
                });
    }

    @PostMapping(value = "/register/stockinit" , headers = "Accept=application/json; charset=utf-8") /*headers = type de la réponse*/
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Product is registered" ) // message pour l'utilisateur (ne change pas ce qui se passe ds base de donnée)
    public Mono<String> create(@RequestBody StockinitRequest stockinit) {

        if( ObjectUtils.anyNotNull(stockinit)  && !ObjectUtils.allNotNull(stockinit.getId(),stockinit.getMagasin() )){
            log.error("Validation error: one of parameter is not found");
            return Mono.error(new ValidationParameterException("Validation error" ));
        }
        return Mono.just(stockinit)
                .map(data->
                {
                     return stockinitService.register(data).subscribe().toString();
                });
    }
    

    @GetMapping
    @RequestMapping(value = "/customers{customername}")

    public Flux<Customer> getCustomers(@RequestParam(required = true, name = "customername") String customername ) {
        log.info("Searching  {} ",customername );
        return customerService.searchName(customername)

                // uses of doNext
                .doOnNext(customer -> log.info(customer.getEmail()+ " is found"));
    }



    @GetMapping
    @RequestMapping(value = "/customers/")
    public Flux<Customer> getCustomers() {
        log.info("All customers searching");
      return customerService.getCustomers()
              // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( data-> data);
    }
    
    @GetMapping
    @RequestMapping(value = "/products/")
    public Flux<Product> getProducts() {
        log.info("All customers searching");
      return productService.getProduct()
              // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( product-> product);
    }
    
    @GetMapping
    @RequestMapping(value = "/stocks/")
    public Flux<Stockinit> getStockinit() {
        log.info("All customers searching");
      return stockinitService.getStockinit()
              // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( stockinit-> stockinit);
    }
}

