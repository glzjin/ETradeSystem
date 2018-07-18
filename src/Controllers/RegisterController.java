package Controllers;

import Helpers.ModelHelper;
import Helpers.UserHelper;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    static String to_reg_page_url = "/register";
    static String to_home_page_url = "/";

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String index() {
        return "register/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("user_role_type") String user_role_type,
                           Model model, HttpSession session) {
        //输入参数为空
        if(username.equals("") || password.equals("")) {
            model.addAttribute("toURL", to_reg_page_url);
            model.addAttribute("resultMsg", "信息不完整，请重新填写。");
            return "register/result";
        }

        if(password.length() < 6) {
            model.addAttribute("toURL", to_reg_page_url);
            model.addAttribute("resultMsg", " 密码过短（小于六位），请重新填写。");
            return "register/result";
        }

        //预处理参数
        Byte userRoleType = Byte.valueOf(user_role_type);
        if(userRoleType != UsersEntity.USER_ROLE_TYPE_BUYER && userRoleType != UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH) {
            model.addAttribute("toURL", to_reg_page_url);
            model.addAttribute("resultMsg", " 用户角色不正确。");
            return "register/result";
        }

        try{
            UsersEntity user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("userName", username));

            if(user != null) {
                model.addAttribute("toURL", to_reg_page_url);
                model.addAttribute("resultMsg", "用户名已存在，请重新输入。");
                return "register/result";
            }

            //创建一个新用户
            UsersEntity new_user = new UsersEntity();
            new_user.setUserName(username);
            new_user.savePassword(password);
            new_user.setUserRoleType(userRoleType);
            //保存用户到数据库
            ModelHelper.createSingle(new_user);

            model.addAttribute("toURL", to_home_page_url);
            model.addAttribute("resultMsg", "注册成功。");

            //把用户信息加到 Session 里，自动登录
            UserHelper.setCurrentUser(session, new_user);

            return "register/result";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("toURL", to_reg_page_url);
            model.addAttribute("resultMsg", " 系统发生了错误！");
            return "register/result";
        }
    }
}
