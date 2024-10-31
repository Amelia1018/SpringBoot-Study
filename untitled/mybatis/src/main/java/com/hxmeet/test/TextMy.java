package com.hxmeet.test;

import com.hxmeet.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
public class TextMy{
    public static void main(String[] args) throws IOException {
        // 加载核心配置文件路径
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        //使用SqlSession来执行SQL语句
        List<User> user = sqlSession.selectList("UserMapper.findAllUsers");
        System.out.println(user);
        sqlSession.close();


    }
}

