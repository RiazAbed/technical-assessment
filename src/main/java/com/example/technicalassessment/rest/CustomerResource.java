package com.example.technicalassessment.rest;

import com.example.technicalassessment.models.Customer;
import com.example.technicalassessment.service.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws URISyntaxException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());

        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Customer newCustomer = optionalCustomer.get();

        newCustomer.setCustomerFirstName(customer.getCustomerFirstName());
        newCustomer.setCustomerLastName(customer.getCustomerLastName());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setCustomerStatus(customer.isCustomerStatus());

        Customer persistedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(persistedCustomer);

    }

    @DeleteMapping(path = "/{customernumber}", produces = "application/json")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String customernumber) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customernumber);

        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(customernumber);

        return ResponseEntity.ok(optionalCustomer.get());
    }

    @GetMapping(path = "/{customernumber}", produces = "application/json")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customernumber) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customernumber);
        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalCustomer.get());
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomer(){
        return ResponseEntity.ok(customerRepository.findAll());
    }

}
