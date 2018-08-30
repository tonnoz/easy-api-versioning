package com.example.hateoas.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
        final Animal cat = new Animal();
        cat.setName(name);
        cat.setKind("cat");
        cat.setVersion("v2");
        cat.setCanFly(true);

        cat.add(
            linkTo(
                methodOn(ApiVersioningDemoApp.class).upToDateCatEndpoint(name)
            ).withSelfRel()
        );

        cat.add(
            linkTo(
                methodOn(ApiVersioningDemoApp.class).upToDateDogEndpoint(name)
            ).withRel("dog"));
        return cat;
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
    class Animal extends ResourceSupport {
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



}
