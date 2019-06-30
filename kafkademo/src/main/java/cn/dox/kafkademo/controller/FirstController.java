package cn.dox.kafkademo.controller;

import cn.dox.kafkademo.mq.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: weidx
 * @date: 2019/6/30
 */

@RestController
public class FirstController {

    @Autowired
    private KafkaSender kafkaSender;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/send")
    public String send(@RequestParam("user") String user) {
        kafkaSender.send("topic1", user);
        return "success";
    }
}

