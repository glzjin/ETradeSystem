package Controllers.Buyer;

import Helpers.*;
import Models.CartInfosEntity;
import Models.ItemsEntity;
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
public class BuyerCartInfosController {
    //默认页数
    static int PageSize = 10;

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param page
     *             页数
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/buyer/cart_infos", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page,
                        Model model,
                        HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            List list = PageHelper.pagenation(CartInfosEntity.class, Integer.valueOf(page), Restrictions.eq("user", current_user));
            model.addAttribute("List", list);

            //获取总记录数，计算总页面数，传递总页面数
            int total_page = PageHelper.pageCount(CartInfosEntity.class, Restrictions.eq("user", current_user));
            model.addAttribute("totalPage", total_page);

            return "buyer/cart_infos/index";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }



    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param id
     *            商品 id
     * @param page
     *            当前页数
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/buyer/cart_infos/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(value = "item_id") String id,
                         @RequestParam(value = "page", defaultValue = "1") String page,
                         Model model,
                         HttpSession session) {

        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);
            Integer itemId = Integer.valueOf(id);

            ItemsEntity item = (ItemsEntity) ModelHelper.getSingle(ItemsEntity.class, Restrictions.eq("id", itemId));

            CartHelper.delCart(current_user, item);

            //返回信息
            MsgHelper.setMsg(session, "商品ID:" + id + "从购物车里删除成功！");
            return "redirect:/buyer/cart_infos?page=" + currentPage;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param id
     *            商品 id
     * @param page
     *            当前页数
     * @param model
     *            一个域对象，可用于存储数据值
     * @param operation
     *            操作，1为增，-1为减
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/buyer/cart_infos/edit", method = RequestMethod.POST)
    public String edit(@RequestParam(value = "item_id") String id,
                         @RequestParam(value = "page", defaultValue = "1") String page,
                         @RequestParam(value = "operation", defaultValue = "1") String operation,
                         Model model,
                         HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);
            Integer itemId = Integer.valueOf(id);
            Integer operation_d = Integer.valueOf(operation);

            ItemsEntity item = (ItemsEntity) ModelHelper.getSingle(ItemsEntity.class, Restrictions.eq("id", itemId));

            UsersEntity user = (UsersEntity)session.getAttribute("user");

            if(operation_d == 1) {
                CartHelper.addCart(user, item, true);
            } else {
                CartHelper.addCart(user, item, false);
            }

            //返回信息
            MsgHelper.setMsg(session, "商品ID:" + id + "数量编辑成功！");
            return "redirect:/buyer/cart_infos?page=" + currentPage;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }
}
