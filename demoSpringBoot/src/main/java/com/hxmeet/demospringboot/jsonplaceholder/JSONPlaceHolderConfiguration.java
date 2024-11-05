package com.hxmeet.demospringboot.jsonplaceholder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSONPlaceHolderConfiguration {

    @Bean("jsonplaceholder")
    CommandLineRunner runner(JSONPlaceHolderClient jsonPlaceHolderClient) {
        return args -> {
            System.out.println("http://jsonplaceholder.typicode.com/");
            System.out.println(jsonPlaceHolderClient.getPosts().size());

            System.out.println();
            System.out.println("http://jsonplaceholder.typicode.com/1");
            System.out.println(jsonPlaceHolderClient.getPost(1));
        };
    }
}
