package com.hxmeet.demospringboot.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Component
/*它允许 Spring 自动检测自定义 Bean。
换句话说，无需编写任何明确的代码，Spring 就能做到：
    扫描应用，查找注解为 @Component 的类
    将它们实例化，并注入任何指定的依赖
    在需要的地方注入
    Spring 提供了一些专门的元注解：
    @Controller、@Service 和 @Repository。它们都提供了与 @Component 相同的功能。
它们的作用都是一样的，因为它们都是由 @Component 作为元注解组成的注解。
它们就像 @Component 别名，在 Spring 自动检测或依赖注入之外有专门的用途和意义。
理论上，如果愿意，可以只使用 @Component 来满足我们对 Bean 自动检测的需求。
反过来，也可以编写使用 @Component 的专用注解。*/
@Service
public class CustomerService {
    private static CustomerRebuy CustomerRebuy ;
/*@Qualifier注解是用来解决特定类型的bean存在多个候选者时，
如何选择正确的bean进行注入的问题。这个注解通常与@Autowired注解一起使用
@Autowired默认按类型进行自动装配，如果有多个相同类型的bean，则需要@Qualifier来指定具体的bean。*/


    /*public  CustomerService(@Qualifier("fake")CustomerRebuy customerRebuy){

        this.CustomerRebuy= customerRebuy;
    }*/
    @Autowired
    public  CustomerService(CustomerRebuy customerRebuy){

        this.CustomerRebuy= customerRebuy;
    }
    static List<Customer>getCustomer() {
        return CustomerRebuy.getCustomers();
    }


}
