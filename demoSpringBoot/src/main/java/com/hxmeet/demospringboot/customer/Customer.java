package com.hxmeet.demospringboot.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
//这俩个注释允许我们把这个Customer类映射到Table上，在我们数据库中
public class Customer {
    @Id
    private Long id;
    @NotBlank(message = "name is not empty")
    private String name;
    /*下面有个JsonIgnore表示忽略password,但是这里又添加这个注解，表示这个允许我们 post password，
    但是不能get password*/
    @NotBlank(message = "password is not empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;

    @NotBlank(message = "email is not empty")
    @Email
    private String email;

    Customer(Long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Customer() {
    }

    @JsonProperty("customer-id")
    /*这个Json注解更改名称，将原来的id变成customer-id,值不变*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @JsonIgnore
    /*这个注解顾名思义就是忽略，这里忽略password
     */
    public String getPassword() {
        return password;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}