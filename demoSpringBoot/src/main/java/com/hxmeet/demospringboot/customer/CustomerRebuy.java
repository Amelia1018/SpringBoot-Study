package com.hxmeet.demospringboot.customer;

import java.util.List;
//包-接口-实现多个-类

/*关于接口的说明:
它不能用于创建对象（在上面的示例中，不可能在MyMain类中创建"Animal"对象）
接口方法没有主体-主体由"implement"类提供
在实现接口时，必须重写其所有方法
默认情况下，接口方法是abstract抽象的和public公共的
接口属性默认为public, static , final
接口不能包含构造函数（因为它不能用于创建对象）
为什么以及何时使用接口?
为了实现安全性-隐藏某些细节，只显示对象（接口）的重要细节。
Java不支持"多重继承"（一个类只能从一个超类继承）。但是，它可以通过接口实现，因为该类可以实现多个接口。*/
public interface CustomerRebuy {
    List<Customer> getCustomers();

}
