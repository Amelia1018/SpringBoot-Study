package com.hxmeet.demospringboot.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerCotroller {
    /*老方法 缺点：不方便测试这个类，因为如果有很多个例子，会实例化多次，没必要*/
    /*private final CustomerService customerService;
    public CustomerCotroller() {
        customerService = new CustomerService();
    }*/
    private final CustomerService customerService;
    @Autowired/*这个注解允许Spring自动解析并注入依赖的Bean到其他Bean中。*/
    public CustomerCotroller(CustomerService customerService, CustomerService customerService1) {

        this.customerService = customerService1;
    }
    @GetMapping
   List<Customer >getCustomer() {
        return  CustomerService.getCustomer();
    }
}
