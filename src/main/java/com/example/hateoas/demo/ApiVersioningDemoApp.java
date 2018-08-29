package com.example.hateoas.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@RestController
@ApiVersion()
public class ApiVersioningDemoApp {


    @GetMapping(path = "/dog/{name}")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Animal upToDateDogEndpoint(@PathVariable String name){
        final Animal dog = new Animal();
        dog.setName(name);
        dog.setKind("dog");
        dog.setVersion("v2");
        dog.setCanFly(true);
        return dog;

    }

    @GetMapping(path = "/cat/{name}")
    public Animal upToDateCatEndpoint(@PathVariable String name){
        final Animal dog = new Animal();
        dog.setName(name);
        dog.setKind("cat");
        dog.setVersion("v2");
        dog.setCanFly(true);
        return dog;
    }

    @ApiVersion("v1")
    @GetMapping(path = "/dog/{name}")
    public Animal deprecatedDogEndpoint(@PathVariable String name){
        final Animal dog = new Animal();
        dog.setName(name);
        dog.setKind("dog");
        dog.setVersion("v1");
        dog.setCanFly(false);
        return dog;
    }

    // example DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Animal{
        private String version;
        private String name;
        private String kind;
        private boolean canFly;
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











    /*
    extends ResourceSupport

     dog.add(
            linkTo(
                methodOn(ApiVersioningDemoApp.class).upToDateDogEndpoint(name)
            ).withSelfRel()
        );

        dog.add(
            linkTo(
                methodOn(ApiVersioningDemoApp.class).upToDateCatEndpoint(name)
            ).withRel("cat")
        );
     */

}
