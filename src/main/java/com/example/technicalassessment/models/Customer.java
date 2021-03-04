package com.example.technicalassessment.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Customer {

    @Id
    private String id;

    private String customerFirstName;
    private String customerLastName;
    private String address;
    private boolean customerStatus;

    public Customer(String customerFirstName, String customerLastName, String address, boolean customerStatus) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.address = address;
        this.customerStatus = customerStatus;
    }
}

