package com.neurocloud.userservice.controller;


import com.neurocloud.common.logging.TraceIdLogger;
import com.neurocloud.userservice.model.Product;
import com.neurocloud.userservice.model.User;
import com.neurocloud.userservice.repository.UserRepository;
import com.neurocloud.userservice.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allUsers")
    public List<User> getAllUsers()
    {
        TraceIdLogger.info("Fetching all users...");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){
        TraceIdLogger.info("Creating user...");
        return userRepository.save(user);
    }

    @GetMapping("/test")
    public String test() {
        return "User Service is working!";
    }

    /*
      ***************** service-service calls ***********************
    Now lets try to fetch the product details from the product-service microservice/module usiing
    user-service microservice, i.e, this microservice itself
    *   user-service --> product-service
     */
    @Autowired
    private RestTemplate restTemplate;

    /* Commenting these because the reformed method is done at the end.

    //Now the method to call the get all the products
    @GetMapping("/getAllProducts")
    public List<Product> getAllProductsFromUser (){
        String productServiceUrl = "http://product-service/products/getAllProducts";
        ResponseEntity<List<Product>> response = restTemplate.exchange(productServiceUrl,
                                                              HttpMethod.GET,
                                                              null,
                                                              new ParameterizedTypeReference<List<Product>>() {}
                                                        );
        return response.getBody();
    }
    */



    /*
     Now service-service calls ofcourse with the help of RestTemplate, but now with the access_token validation.\
     so this needs Token manager which does that.
     */
    @Autowired
     private TokenManager tokenManager;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProductsFromUser () {
        TraceIdLogger.info("Fetching all users...");

        System.out.println("Calling product-service from user-service...");  // Just for debug - remove later
        String productServiceUrl = "http://product-service/products/getAllProducts";

        // Get the token
        String accessToken = tokenManager.getAccessToken();
        System.out.println("Using access token: " + accessToken);  // Just for debug - remove later

        // Set Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                productServiceUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Product>>() {}
        );

        return response.getBody();
    }
}
