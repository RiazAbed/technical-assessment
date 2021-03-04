package com.example.technicalassessment.CustomerResource;

import com.example.technicalassessment.models.Customer;
import com.example.technicalassessment.service.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CustomerRepositoryTests {
    @Autowired
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        customerRepository.save(new Customer("Tom", "Topper", "21 baker street", true));
    }

    @Test
    public void testSetup() {
        Customer testCustomer = customerRepository.findAll().get(0);
        assertEquals(testCustomer.getCustomerFirstName(),"Tom");
        assertEquals(customerRepository.findAll().size(),1);
    }

    @Test
    public void shouldBeNotEmpty() {
        assertThat(customerRepository.findAll()).isNotEmpty();
    }

    @Test
    public void testWrite(){

    }
}
