package im.hxmeet.Xfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true)
@ServletComponentScan(basePackageClasses = {"im.hxmeet.Xfile.core.filter", "im.hxmeet.Xfile.module.storage.filter"})
@ComponentScan(basePackages = "im.hxmeet.Xfile.*")
public class XfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfileApplication.class, args);
    }
}


/*--解释--
@SpringBootApplication:
这是一个组合注解，包含了 @Configuration、@EnableAutoConfiguration 和 @ComponentScan。
@Configuration 表示该类可以被 Spring 容器作为源来定义 bean。
@EnableAutoConfiguration 启用 Spring Boot 的自动配置特性，根据您在 classpath 中加入的包和其他设置来自动配置 Spring 应用。
@ComponentScan 自动扫描当前包及其子包中的组件（如 @Component, @Service, @Repository, @Controller 等）。

@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true):
该注解用于启用基于 AspectJ 的 AOP（面向切面编程）。
exposeProxy = true 表示向切面暴露代理对象，以便在切面中引用当前对象。
proxyTargetClass = true 表示强制使用 CGLIB 代理而不是 JDK 动态代理，从而允许对类进行代理（即使它没有实现任何接口）。

@ServletComponentScan(basePackages = {"...."})
这个注解用于扫描 Servlet 组件，比如 @WebServlet、@WebFilter 和 @WebListener。它指定要扫描的包，允许自动注册这些组件。

@ComponentScan(basePackages = ".....")
该注解定义了要扫描的组件包路径。在这里，它会扫描 im.hxmeet.Xfile 包及其所有子包，并注册所有的 Spring 组件。


总结来说，这段代码配置了一个 Spring Boot 应用程序，启用了 AOP 和 Servlet 支持，并指定了组件扫描的相关包路径，
从而便于管理和配置应用中的各个部分。


* */