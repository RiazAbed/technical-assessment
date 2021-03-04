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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
@AutoConfigureMockMvc
public class CustomerResourceTests {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private MockMvc mvc;

    private final String rootContext = "/customer";

    @Before
    public void setUp() throws Exception {
        customerRepository.save(new Customer("Tom", "Topper", "21 baker street", true));
    }

    @Test
    public void TestInsertCustomer() throws Exception {
        int collectionSize = customerRepository.findAll().size();
        Customer newCustomer = new Customer("Jack", "Black", "1 zebra street", true);


        MockHttpServletRequestBuilder requestBuilder = put(rootContext +"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(asJson(newCustomer));
        mvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertTrue(customerRepository.findAll().size() > collectionSize);
    }


    private String asJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
