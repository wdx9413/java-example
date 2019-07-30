package cn.dox.jdbcdemo.controller;

import cn.dox.jdbcdemo.model.vo.Response;
import cn.dox.jdbcdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.Date;

import static cn.dox.jdbcdemo.common.constant.ResponseEnum.*;

/**
 * @author: weidx
 * @date: 2019/7/28
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("add")
    public Response<String> add(String username, @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDate) {
        userService.add(username, birthDate);
        return Response.getInstance(OK, "");
    }

}
