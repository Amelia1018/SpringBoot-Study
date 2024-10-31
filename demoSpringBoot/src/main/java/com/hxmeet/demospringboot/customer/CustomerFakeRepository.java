package com.hxmeet.demospringboot.customer;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//@Component(value = "fake")
//@Repository(value="fake")
public class CustomerFakeRepository implements CustomerRebuy{
    @Override
    public List<Customer> getCustomers() {
        return Arrays.asList(
                new Customer(1,"jennie kim"),
                new Customer(2,"jisoo kim")
        );
    }
}
