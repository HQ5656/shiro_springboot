package cn.hq.shiro_springboot.service;

import cn.hq.shiro_springboot.mapper.UserMapper;
import cn.hq.shiro_springboot.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUserByName(String name) {
        //测试session是否一样  默认和http的session一致
/*        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String test = (String) session.getAttribute("test");
        System.out.println(test);*/
        return userMapper.queryUserByName(name);
    }
}
