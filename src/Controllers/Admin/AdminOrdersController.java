package Controllers.Admin;

import Helpers.ModelHelper;
import Helpers.PageHelper;
import Models.OrdersEntity;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminOrdersController {
    //默认页数
    static int PageSize = 10;

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param page
     *             页数
     * @param model
     *            一个域对象，可用于存储数据值
     */
    @RequestMapping(value = "/admin/orders", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page, Model model) {
        try{
            List list = PageHelper.pagenation(OrdersEntity.class, Integer.valueOf(page));

            model.addAttribute("List", list);

            //获取总记录数，计算总页面数，传递总页面数
            int total_page = PageHelper.pageCount(UsersEntity.class);
            model.addAttribute("totalPage", total_page);


            return "admin/orders/index";
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
     * @param id
     *            订单 ID
     */
    @RequestMapping(value = "/admin/orders/detail", method =  RequestMethod.GET)
    public String detail(@RequestParam("id") String id,
                         Model model) {
        try{
            int orderId = Integer.valueOf(id);
            //获取可购买商品信息

            OrdersEntity order = (OrdersEntity) ModelHelper.getSingle(OrdersEntity.class, Restrictions.eq("id", orderId));

            model.addAttribute("Order", order);

            return "admin/orders/detail";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "admin/fail";
        }
    }
}
