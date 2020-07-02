package cn.hq.shiro_springboot;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 * @author Hey
 * @since 0.9 RC2
 */
class QuickStart {

    //日志门面，后面可以通过log输出一些日志信息 使用了log4j日志框架
    private static final transient Logger log = LoggerFactory.getLogger(QuickStart.class);

    public static void main(String[] args) {
// 1. 创建一个SecurityManagerFactory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2. 通过SecurityManagerFactory获取一个SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        // 3. 把securityManager设置到SecurityUtils, 方便后期获取(全局)
        SecurityUtils.setSecurityManager(securityManager);
        // 4. 创建一个Subject （可以当成当前登录的用户）
        Subject currentUser = SecurityUtils.getSubject();
        // 5. 给Subject创建一个session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("从session中获取到的value [" + value + "]");
        }

        // *6. 判断当前用户是否被认证
        if (!currentUser.isAuthenticated()) {
            // 如果没有登录就进行登录
            // 6.1 封装用户名和密码
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // 6.2 设置记住我，暂时不用，先注销
            // token.setRememberMe(true);
            try {
                // 6.3 登录
                currentUser.login(token);
            } catch (UnknownAccountException uae) { // 6.3.1 未知的账号异常
                log.info("未知的账号异常" + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {   // 6.3.2 用户名或密码错误
                log.info("用户名或密码错误 " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {  // 6.3.3 账户被锁定
                log.info("账户被锁定" + token.getPrincipal());
            } catch (AuthenticationException ae) { // 6.3.4 其他的认证异常
                // AuthenticationException是上面三个异常的父类
            }
        }

        // 7. 获取登录的用户名
        log.info("欢迎 [" + currentUser.getPrincipal() + "] 登录成功.");

        // 8. 判断该用户是否有某个角色
        if (currentUser.hasRole("schwartz")) {
            log.info("该用户有【schwartz】这个角色!");
        } else {
            log.info("该用户没有【schwartz】这个角色!");
        }

        // 9. 判断用户是否有这个权限
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("该用户有【lightsaber:weild】这个角色.");
        } else {
            log.info("该用户没有【lightsaber:weild】这个角色.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        // 10. 注销
        currentUser.logout();
        // 11. 系统退出
        System.exit(0);

    }
}
