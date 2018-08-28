package com.example.hateoas.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@RestController
@ApiVersion()
public class ApiVersioningDemoApp {


    @GetMapping(path = "/dog")
    public Animal upToDateDogEndpoint(){
        final Animal dog = new Animal();
        dog.setName("Bobbi");
        dog.setKind("dog");
        dog.setVersion("v2");
        dog.setCanFly(true);
        return dog;
    }

    @ApiVersion("v1")
    @GetMapping(path = "/dog")
    public Animal deprecatedDogEndpoint(){
        final Animal dog = new Animal();
        dog.setName("Bobbi");
        dog.setKind("dog");
        dog.setVersion("v1");
        dog.setCanFly(false);
        return dog;
    }

    // example DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Animal {
        private String version;
        private String name;
        private String kind;
        private boolean canFly;
        private boolean hasLongHair;
    }




    public static void main(String[] args) {
        SpringApplication.run(ApiVersioningDemoApp.class, args);
    }


    @Configuration
    public class WebMvcConfig extends WebMvcConfigurationSupport {
        @Override
        public RequestMappingHandlerMapping requestMappingHandlerMapping() {
            return new ApiVersionRequestMapping("");
        }
    }
    
}
