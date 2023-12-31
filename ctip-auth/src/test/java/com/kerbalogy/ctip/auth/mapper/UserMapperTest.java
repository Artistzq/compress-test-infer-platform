package com.kerbalogy.ctip.auth.mapper;

import com.kerbalogy.ctip.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}