package com.hxmeet.demospringboot.customer;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public  class CustomerFakeRepository implements CustomerRebuy{
    //@Override
    public List<Customer> getCustomers() {
        return Arrays.asList(
                new Customer(1L,"jiennie","password123","Jiennie@qq.com"),
                new Customer(2L,"jisso","password456","jisso@qq.com")
        );
    }

}
