package com.hxmeet.mapper;


import com.hxmeet.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> findAllUsers();

    User findUserById();

    User  addUser();

    User deleteUserById();

    User updatePasswordById();


}