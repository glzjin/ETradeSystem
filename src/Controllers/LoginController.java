package Controllers;

import Helpers.ModelHelper;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param username
     *            用户名，一定要对应着表单的name才行
     * @param password
     *            用户密码，也应该对应表单的数据项
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象，用于往线程里丢东西
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST) // @RequestMapping 注解可以用指定的URL路径访问本控制层
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        Model model, HttpSession session) {

        //输入参数为空
        if(username.equals("") || password.equals("")) {
            model.addAttribute("error_msg", "信息不完整，请重新填写。");
            return "login/fail";
        }

        try{
            UsersEntity user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("userName", username));

            //告诉页面该显示哪个用户名
            model.addAttribute("username", username);
            if(user == null) {
                //添加错误信息
                model.addAttribute("error_msg", " 用户不存在！");
                return "login/fail";
            } else {
                //获取密码进行比较
                if(!user.verifyPassword(password)) {
                    model.addAttribute("error_msg", "  密码错误！");
                    return "login/fail";
                }

                if(user.getUserRoleType() == UsersEntity.USER_ROLE_TYPE_BANNED) {
                    model.addAttribute("error_msg", "  您的账户已被封禁，无法登录！");
                    return "login/fail";
                }

                model.addAttribute("user_role_type", user.getUserRoleType());

                //把用户信息存 Session
                session.setAttribute("user", user);

                return "login/success";
            }
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "login/fail";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
        return "login/index";
    }
}
