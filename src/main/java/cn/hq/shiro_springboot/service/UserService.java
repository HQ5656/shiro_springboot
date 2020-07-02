package cn.hq.shiro_springboot.service;

import cn.hq.shiro_springboot.pojo.User;

public interface UserService {
    public User queryUserByName(String name);
}
