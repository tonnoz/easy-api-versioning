package com.example.hateoas.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// API VERSIONING INTERFACE
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    String[] value() default {"", "v2"}; //ensure here is always mapped with "" and the API LAST VERSION
}