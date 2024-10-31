package com.hxmeet.demospringboot.customer;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
//@Primary
/*在Spring框架中，@Primary注解用于在多个相同类型的bean存在时，
标记一个bean为首选bean。当Spring容器进行自动装配时，它会优先选择被@Primary标记的bean进行注入。
使用@Primary注解的情景
当我们在Spring配置类中定义多个相同类型的bean时，我们可以使用@Primary注解来指定一个bean作为首选bean。
例如，如果我们有两个DataSource类型的bean，并且我们希望Spring在自动装配时优先选择其中一个，
我们可以在该bean的定义上添加@Primary注解。*/

/*implements 关键字用于实现interface接口。
interface 关键字用于声明仅包含抽象方法的特殊类型的类。
要访问接口方法，接口必须由另一个具有implements关键字（而不是 extends）的类"实现"（类似于继承）。
接口方法的主体由"implement"类提供。*/
public class CustomerRepository implements  CustomerRebuy{
    @Override
    public List<Customer> getCustomers() {
        /*连接真正的数据库*/
        return Collections.singletonList(

                new Customer(2, "to do implement real db")
        );
    }
}
