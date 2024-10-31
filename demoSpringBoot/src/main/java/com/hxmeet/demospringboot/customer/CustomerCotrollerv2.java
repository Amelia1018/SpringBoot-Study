package com.hxmeet.demospringboot.customer;

import com.hxmeet.demospringboot.exception.ApiRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//表示忽略这个api


/*这个注解是请求映射路径，浏览器上的localhost:8084要加上这里的path*/
@RequestMapping(path="api/v2/customers")

@RestController
public class CustomerCotrollerv2 {

    private final CustomerService customerService;
    @Autowired
    public CustomerCotrollerv2(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    List<Customer> getCustomers() {
        return  customerService.getCustomers();
    }

   @GetMapping(path="{customerId}")
    Customer getCustomer(@PathVariable ( "customerId")Long id) {

        return (Customer) customerService.getCustomer(id);
               /* getCustomers()
                .stream()
                .filter(customer -> customer.getId()==id)
                .findFirst()
                .orElseThrow(()->new RuntimeException("Customer not found"));*/
    }
//自定义抛出的异常
    @GetMapping(path="{customerId}/exception")
    Customer getCustomerException(@PathVariable ( "customerId")Long id) {
        throw new ApiRequestException("ApiRequestException for customer"+id);

    }


    @PostMapping
    /*@Validated 表示一定要有效*/
    void createNewCustomer(@Valid  @RequestBody Customer customer) {
        System.out.println("POST REQUEST...");
        System.out.println(customer);
    }

    @PutMapping
    void updateCustomer( @RequestBody Customer customer) {
        System.out.println("UPDATE REQUEST...");
        System.out.println(customer);
    }

    @DeleteMapping(path="{customerId}")
    void deleteCustomer(@PathVariable ( "customerId")Long id) {
        System.out.println("DELETE REQUEST FOR CUSTOMER WITH ID: "+ id);

    }
}
