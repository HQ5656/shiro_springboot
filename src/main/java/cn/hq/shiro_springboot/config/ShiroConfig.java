package cn.hq.shiro_springboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        Map<String,String> filterMap = new LinkedHashMap<>();
        
        //授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        
       /* filterMap.put("/user/add","authc");
        filterMap.put("/user/update","authc");*/
       //登陆拦截
        filterMap.put("/user/*","authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置登陆的地址
        filterFactoryBean.setLoginUrl("/user/login");
        //设置未授权url
        filterFactoryBean.setUnauthorizedUrl("/unauth");
        return filterFactoryBean;
    }
    
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return  securityManager;
    }
    
    @Bean
    public  UserRealm userRealm(){
        return  new UserRealm();
    }
     
    //整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return  new ShiroDialect();
    }
}
