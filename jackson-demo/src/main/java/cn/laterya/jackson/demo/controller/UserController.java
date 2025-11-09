package cn.laterya.jackson.demo.controller;

import cn.laterya.jackson.demo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 返回 JSON（自动序列化）
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        User user = new User("张三", 28);
        user.setPassword("123456"); // 会被 @JsonIgnore 忽略
        return user;
    }

    // 接收 JSON 请求体（自动反序列化）
    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println("收到用户: " + user.getName() + ", 创建时间: " + user.getCreateTime());
        return user; // 返回给前端
    }

    // 返回列表
    @GetMapping
    public List<User> listUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("李四", 22));
        users.add(new User("王五", 30));
        return users;
    }
}