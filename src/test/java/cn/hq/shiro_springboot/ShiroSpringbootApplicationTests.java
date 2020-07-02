package cn.hq.shiro_springboot;

import cn.hq.shiro_springboot.pojo.User;
import cn.hq.shiro_springboot.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {
    @Autowired
    private  UserServiceImpl userService;
    @Test
    void contextLoads() {
        System.out.println(userService.queryUserByName("111"));
    }

}
