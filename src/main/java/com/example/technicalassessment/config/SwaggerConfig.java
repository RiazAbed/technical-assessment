package com.example.technicalassessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        ArrayList<ResponseMessage> responseMessages = configureGlobalResponeMessages();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.technicalassessment.rest"))
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, responseMessages);
        return docket;
    }

    private ArrayList<ResponseMessage> configureGlobalResponeMessages() {
        ArrayList<ResponseMessage> newArrayList = new ArrayList<>();

        newArrayList.add(new ResponseMessageBuilder()
                .code(401)
                .message("Post denied. You do not have admin permissions")
                .responseModel(new ModelRef("Error")).build());

        return newArrayList;
    }

    private ApiInfo metaData() {
        ArrayList<VendorExtension> vendors = new ArrayList<>();
        VendorExtension a = new VendorExtension() {
            @Override
            public String getName() {
                return "Riaz Abed";
            }

            @Override
            public Object getValue() {
                return null;
            }
        };
        vendors.add(a);

        return new ApiInfo(
                "Customer Technical Assessment",
                "This REST API exposes all the functionality of the customer technical assessment",
                "1.0",
                "Terms of service",
                new Contact("Riaz Abed", "https://github.com/RiazAbed/technical-assessment", "riazabed@hotmail.com"),
                "None",
                "None",
                vendors);

    }

}
