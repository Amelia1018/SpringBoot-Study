package com.hxmeet.demospringboot.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {
    @Value("${app.useFakeCustomerRebuy:false}")
    private Boolean useFakeCustomerRebuy;
    @Bean
    /*意味着实例化，里面任何代码都会被执行*/
    CommandLineRunner commandLineRunner()
    {
        return args -> {
            System.out.println("Command line runner hooray");
        };
    }

    @Bean
    CustomerRebuy customerRebuy() {
        System.out.println("CustomerRebuy = "+ useFakeCustomerRebuy );
        return useFakeCustomerRebuy ? new CustomerFakeRepository() : new CustomerRepository();
    }
}
