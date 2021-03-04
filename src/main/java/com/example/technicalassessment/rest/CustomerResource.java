package com.example.technicalassessment.rest;

import com.example.technicalassessment.models.Customer;
import com.example.technicalassessment.service.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/customer")
public class CustomerResource {
    @Autowired
    CustomerRepository customerRepository;

    @PutMapping("/")
    public ResponseEntity<Customer> insertCustomer(@RequestBody Customer customer) throws URISyntaxException {
        Customer persistedCustomer = customerRepository.save(customer);
        return ResponseEntity.created(new URI("/customer/" + customer.getId())).body(persistedCustomer);
    }

}
