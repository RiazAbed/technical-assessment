package com.example.technicalassessment.CustomerResource;

import com.example.technicalassessment.models.Customer;
import com.example.technicalassessment.service.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
@AutoConfigureMockMvc
public class CustomerResourceTests {

    private final String rootContext = "/customer";
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        customerRepository.save(new Customer("Tom", "Topper", "21 baker street", true));
    }

    @Test
    public void testInsertCustomer() throws Exception {
        int collectionSize = customerRepository.findAll().size();
        Customer newCustomer = new Customer("Jack", "Black", "1 zebra street", true);
        MockHttpServletRequestBuilder requestBuilder = put(rootContext + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(asJson(newCustomer));
        mvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertTrue(customerRepository.findAll().size() > collectionSize);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer newCustomer = customerRepository.findAll().get(0);
        newCustomer.setCustomerFirstName("New first name");
        newCustomer.setCustomerLastName("New last name");

        MockHttpServletRequestBuilder requestBuilder = post(rootContext + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(asJson(newCustomer));
        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertEquals(customerRepository.findAll().get(0).getCustomerFirstName(), "New first name");
        assertEquals(customerRepository.findAll().get(0).getCustomerLastName(), "New last name");
        assertNotEquals(customerRepository.findAll().get(0).getCustomerFirstName(), "Tom");
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = customerRepository.findAll().get(0);
        assertTrue(customerRepository.findById(customer.getId()).isPresent());
        MockHttpServletRequestBuilder requestBuilder = delete(rootContext + "/" + customer.getId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertFalse(customerRepository.findById(customer.getId()).isPresent());
    }

    @Test
    public void testGetCustomer() throws Exception {
        String customerId = customerRepository.findAll().get(0).getId();
        MockHttpServletRequestBuilder requestBuilder = get(rootContext + "/" + customerId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(customerId));
    }

    @Test
    public void testGetCustomers()throws Exception{
        customerRepository.save(new Customer("Sam", "Samus", "12 Ball street", true));
        customerRepository.save(new Customer("Jack", "Black", "11 Rock street", true));
        customerRepository.save(new Customer("Luke", "Walker", "6 Star street", true));

        int numberOfCustomers = customerRepository.findAll().size();
        MockHttpServletRequestBuilder requestBuilder = get(rootContext + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
      String resultJson = result.getResponse().getContentAsString();
        List<Customer> customers = new ObjectMapper().readValue(resultJson,List.class);
        assertEquals(numberOfCustomers,customers.size());

    }


    private String asJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
