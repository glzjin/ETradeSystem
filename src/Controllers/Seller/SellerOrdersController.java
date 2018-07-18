package Controllers.Seller;

import Helpers.ModelHelper;
import Helpers.MsgHelper;
import Helpers.PageHelper;
import Helpers.UserHelper;
import Models.OrdersEntity;
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
public class SellerOrdersController {
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
    @RequestMapping(value = "/seller/orders", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page, Model model, HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            List list = PageHelper.pagenation(OrdersEntity.class, Integer.valueOf(page), Restrictions.eq("sellUser", current_user));
            model.addAttribute("List", list);

            //获取总记录数，计算总页面数，传递总页面数
            int total_page = PageHelper.pageCount(OrdersEntity.class, Restrictions.eq("sellUser", current_user));
            model.addAttribute("totalPage", total_page);

            return "seller/orders/index";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "seller/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param model
     *            一个域对象，可用于存储数据值
     * @param id
     *            订单 ID
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/seller/orders/detail", method =  RequestMethod.GET)
    public String detail(@RequestParam("id") String id,
                         Model model,
                         HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            int order_id = Integer.valueOf(id);
            OrdersEntity order = (OrdersEntity) ModelHelper.getSingle(OrdersEntity.class, Restrictions.and(Restrictions.eq("id", order_id),
                    Restrictions.eq("sellUser", current_user)));

            //当前页传递
            model.addAttribute("Order", order);

            return "seller/orders/detail";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "seller/fail";
        }
    }

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param model
     *            一个域对象，可用于存储数据值
     * @param id
     *            订单 ID
     * @param page
     *            页数
     * @param deliver_company
     *            承运公司
     * @param deliver_order_id
     *            承运单号
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/seller/orders/deliver", method =  RequestMethod.POST)
    public String deliver(@RequestParam("id") String id,
                          @RequestParam(value = "page", defaultValue = "1") String page,
                          @RequestParam(value = "deliver_company") String deliver_company,
                          @RequestParam(value = "deliver_order_id") String deliver_order_id,
                          Model model,
                          HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            int order_id = Integer.valueOf(id);

            OrdersEntity order = (OrdersEntity) ModelHelper.getSingle(OrdersEntity.class, Restrictions.and(
                    Restrictions.eq("id", order_id),
                    Restrictions.eq("sellUser", current_user),
                    Restrictions.eq("status", OrdersEntity.STATUS_DELIVER_PENDING)));

            order.setDeliverCompany(deliver_company);
            order.setDeliverOrderId(deliver_order_id);
            order.setStatus(OrdersEntity.STATUS_RECEIVE_PENDING);

            ModelHelper.updateSingle(order);

            MsgHelper.setMsg(session, "订单送货信息更新成功！");
            return "redirect: /seller/orders?page=" + page;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "seller/fail";
        }
    }
}
