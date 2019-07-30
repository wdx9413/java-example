package cn.dox.jdbcdemo.service;

import cn.dox.jdbcdemo.mapper.UserMapper;
import cn.dox.jdbcdemo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author: weidx
 * @date: 2019/7/28
 */

@Service
public class UserService {

    @Autowired UserMapper userMapper;

    public void add(String username, Date birthDate) {
        User user = new User();
        user.setId((long)(Math.random() * 100));
        user.setUsername(username);
        user.setBirthDate(birthDate);
        userMapper.insertA(user);
    }
}
