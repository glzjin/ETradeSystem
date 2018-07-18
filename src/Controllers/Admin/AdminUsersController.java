package Controllers.Admin;

import Helpers.ModelHelper;
import Helpers.MsgHelper;
import Helpers.PageHelper;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminUsersController {
    //默认页数
    static int PageSize = 10;


    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param page
     *             页数
     * @param model
     *            一个域对象，可用于存储数据值
     */
    @RequestMapping("/admin/users")
    public String index(@RequestParam(value = "page", defaultValue = "1") String page, Model model) {
        List list = PageHelper.pagenation(UsersEntity.class, Integer.valueOf(page));
        model.addAttribute("List", list);

        //获取总记录数，计算总页面数，传递总页面数
        int total_page = PageHelper.pageCount(UsersEntity.class);
        model.addAttribute("totalPage", total_page);

        return "admin/users/index";
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param id
     *            用户 id
     * @param page
     *            当前页数
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/admin/users/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(value = "id") String id,
                         @RequestParam(value = "page", defaultValue = "1") String page,
                         Model model,
                         HttpSession session) {

        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);
            Integer userId = Integer.valueOf(id);

            UsersEntity user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("id", userId));

            user.setUserRoleType(UsersEntity.USER_ROLE_TYPE_BANNED);

            ModelHelper.updateSingle(user);

            //返回信息
            MsgHelper.setMsg(session, "ID:" + id + "封禁成功！");
            return "redirect:/admin/users?page=" + currentPage;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "admin/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param id
     *            用户 id
     * @param model
     *            一个域对象，可用于存储数据值
     */
    @RequestMapping(value = "/admin/users/edit", method = RequestMethod.GET)
    public String edit_show(@RequestParam(value = "id") String id,
                            Model model) {

        try{
            //参数预处理
            Integer userId = Integer.valueOf(id);

            UsersEntity user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("id", userId));

            //给管理页面加上要编辑的用户信息
            model.addAttribute("User", user);

            return "admin/users/edit";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "admin/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param id
     *            用户 id
     * @param page
     *            当前页数
     * @param username
     *             用户名
     * @param password
     *            密码
     * @param user_role_type
     *            角色
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/admin/users/edit", method = RequestMethod.POST)
    public String edit(@RequestParam(value = "id") String id,
                       @RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "username") String username,
                       @RequestParam(value = "password") String password,
                       @RequestParam(value = "user_role_type") String user_role_type,
                       Model model,
                       HttpSession session) {
        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);
            Integer userId = Integer.valueOf(id);
            Byte userRoleType = Byte.valueOf(user_role_type);

            //输入参数为空
            if(username.equals("")) {
                //返回 错误信息
                MsgHelper.setMsg(session, "ID:" + id + " 编辑失败，参数不足！");
                return "redirect:/admin/users?page=" + currentPage;
            }

            if(userRoleType == UsersEntity.USER_ROLE_TYPE_ADMIN) {
                MsgHelper.setMsg(session, "添加失败，类型不正确！");
                return "redirect:/admin/users?page=" + currentPage;
            }

            UsersEntity user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("id", userId));

            user.setUserName(username);
            if(!password.equals("")) {
                user.savePassword(password);
            }
            user.setUserRoleType(userRoleType);

            ModelHelper.updateSingle(user);

            //返回信息
            MsgHelper.setMsg(session, "ID:" + id + " 编辑成功！");
            return "redirect:/admin/users?page=" + currentPage;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "admin/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param model
     *            一个域对象，可用于存储数据值
     */
    @RequestMapping(value = "/admin/users/add", method = RequestMethod.GET)
    public String add_show(Model model) {

        try{
            return "admin/users/add";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "admin/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param page
     *            当前页数
     * @param username
     *             用户名
     * @param password
     *            密码
     * @param user_role_type
     *            角色
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/admin/users/add", method = RequestMethod.POST)
    public String add(@RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "username") String username,
                       @RequestParam(value = "password") String password,
                       @RequestParam(value = "user_role_type") String user_role_type,
                       Model model,
                       HttpSession session) {
        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);
            Byte userRoleType = Byte.valueOf(user_role_type);

            //输入参数为空
            if(username.equals("") || password.equals("")) {
                //返回 错误信息
                MsgHelper.setMsg(session, "添加失败，参数不足！");
                return "redirect:/admin/users?page=" + currentPage;
            }

            //判断是否有重名用户
            UsersEntity exist_user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("userName", username));

            if(exist_user != null) {
                MsgHelper.setMsg(session, "添加失败，同名用户已存在！");
                return "redirect:/admin/users?page=" + currentPage;
            }

            if(userRoleType == UsersEntity.USER_ROLE_TYPE_ADMIN) {
                MsgHelper.setMsg(session, "添加失败，类型不正确！");
                return "redirect:/admin/users?page=" + currentPage;
            }

            UsersEntity new_user = new UsersEntity();
            new_user.setUserName(username);
            new_user.savePassword(password);
            new_user.setUserRoleType(userRoleType);

            ModelHelper.createSingle(new_user);

            //返回信息
            MsgHelper.setMsg(session, "添加成功！");
            return "redirect:/admin/users?page=" + currentPage;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", "系统发生了错误！");
            return "admin/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param id
     *            用户 id
     * @param page
     *            当前页数
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/admin/users/auth", method = RequestMethod.POST)
    public String auth(@RequestParam(value = "id") String id,
                       @RequestParam(value = "page", defaultValue = "1") String page,
                       Model model,
                       HttpSession session) {
        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);
            Integer userId = Integer.valueOf(id);

            UsersEntity user = (UsersEntity) ModelHelper.getSingle(UsersEntity.class, Restrictions.eq("id", userId));

            user.setUserRoleType(UsersEntity.USER_ROLE_TYPE_SELLER_AUTHED);

            ModelHelper.updateSingle(user);

            //返回信息
            MsgHelper.setMsg(session, "ID:" + id + "  验证成功！");
            return "redirect:/admin/users?page=" + currentPage;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "admin/fail";
        }
    }
}
