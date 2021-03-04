package com.example.technicalassessment.service;

import com.example.technicalassessment.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {


}
