package Controllers;

import Helpers.UserHelper;
import Models.UsersEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        // 获取用户
        UsersEntity user = (UsersEntity)session.getAttribute("user");
        //获取用户身份进行跳转
        switch(user.getUserRoleType()) {
            case UsersEntity.USER_ROLE_TYPE_ADMIN://管理员
                //redirect:就是跳转 后面跟着要跳转的路径
                return "redirect:/admin/";
            case UsersEntity.USER_ROLE_TYPE_SELLER_AUTHED://已验证卖家
                return "redirect:/seller/";
            case UsersEntity.USER_ROLE_TYPE_BUYER://买家
                return "redirect:/buyer/";
            case UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH://未验证卖家
                return "redirect:/seller/pending";
            default:
                return "index";
        }
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.removeAttribute("user");
        return "index/logout";
    }

    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("error_msg", "系统不知道怎么了....");
        return "index/error";
    }

    @RequestMapping("/error/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error_404(Model model) {
        model.addAttribute("error_msg", "页面走失了....");
        return "index/error";
    }

    @RequestMapping("/error/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error_500(Model model) {
        model.addAttribute("error_msg", "系统开了一个小差....");
        return "index/error";
    }
}
