package Controllers.Buyer;

import Helpers.*;
import Models.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class BuyerOrdersController {
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
    @RequestMapping(value = "/buyer/orders", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page, Model model, HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            List list = PageHelper.pagenation(OrdersEntity.class, Integer.valueOf(page), Restrictions.eq("user", current_user));
            model.addAttribute("List", list);

            //获取总记录数，计算总页面数，传递总页面数
            int total_page = PageHelper.pageCount(OrdersEntity.class, Restrictions.eq("user", current_user));
            model.addAttribute("totalPage", total_page);

            return "buyer/orders/index";
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
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/buyer/orders/order_pre", method =  RequestMethod.GET)
    public String order_pre(Model model,
                        HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            List list = ModelHelper.all(CartInfosEntity.class, Restrictions.eq("user", current_user));

            if(list.size() == 0) {
                MsgHelper.setMsg(session, " 您购物车是空的呢。");
                return "redirect: /buyer/cart_infos";
            }

            model.addAttribute("List", list);


            return "buyer/orders/order_pre";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }

    /**
     * 下单
     *
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/buyer/orders/order_last", method =  RequestMethod.POST)
    public String order_last(@RequestParam("user_real_name") String user_real_name,
                             @RequestParam("user_phone") String user_phone,
                             @RequestParam("user_address") String user_address,
                             Model model,
                             HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);
        try{

            //保存当前用户信息
            current_user = UserHelper.editUserAddressInfo(session, current_user, user_real_name, user_phone, user_address);

            List list = ModelHelper.all(CartInfosEntity.class, Restrictions.eq("user", current_user));

            if(list.size() == 0) {
                MsgHelper.setMsg(session, " 您购物车是空的呢。");
                return "redirect: /buyer/cart_infos";
            }

            ArrayList<Integer> processed_user_id_list = new ArrayList<Integer>();

            for(int i = 0; i < list.size(); i++) {
                CartInfosEntity cart_info = (CartInfosEntity) list.get(i);

                //下架商品直接跳过
                if(cart_info.getItem().getStatus() == ItemsEntity.STATUS_OFF) {
                    continue;
                }

                UsersEntity current_seller_user = cart_info.getItem().getUser();
                if(processed_user_id_list.contains(current_seller_user.getId())){
                    continue;
                }

                processed_user_id_list.add(current_seller_user.getId());

                //拆单
                ArrayList<CartInfosEntity> child_list = new ArrayList<CartInfosEntity>();
                Double total_price = 0.0;
                for(int j = i; j < list.size(); j++) {
                    CartInfosEntity cart_info_temp = (CartInfosEntity) list.get(i);
                    //下架商品直接跳过
                    if(cart_info_temp.getItem().getStatus() == ItemsEntity.STATUS_OFF) {
                        continue;
                    }

                    if(cart_info_temp.getItem().getUser().getId() == current_seller_user.getId()) {
                        child_list.add(cart_info);
                        total_price += cart_info.getItemAmount() * cart_info.getItem().getItemPrice().doubleValue();

                        //库存不足，直接回滚
                        if(cart_info.getItemAmount() > cart_info.getItem().getItemSku()) {
                            MsgHelper.setMsg(session, "您所要购买的商品库存不足。");
                            return "redirect: /buyer/cart_infos";
                        }
                    }
                }

                //保存订单
                OrdersEntity new_order = new OrdersEntity();
                new_order.setDatetime(new Timestamp(System.currentTimeMillis()));
                new_order.setUser(current_user);
                new_order.setPrice(new BigDecimal(total_price));
                new_order.setSellUser(current_seller_user);
                new_order.setStatus(OrdersEntity.STATUS_DELIVER_PENDING);
                ModelHelper.createSingle(new_order);

                for(int j = 0; j < child_list.size(); j++) {
                    CartInfosEntity cart_info_temp = child_list.get(j);

                    //下架商品直接跳过
                    if(cart_info_temp.getItem().getStatus() == ItemsEntity.STATUS_OFF) {
                        continue;
                    }

                    ItemsEntity item = cart_info_temp.getItem();
                    item.setItemSku(item.getItemSku() - cart_info.getItemAmount());
                    item.setItemSold(item.getItemSold() + cart_info.getItemAmount());
                    ModelHelper.updateSingle(item);

                    OrderItemsEntity new_order_item = new OrderItemsEntity();
                    new_order_item.setItemAmount(cart_info_temp.getItemAmount());
                    new_order_item.setItem(cart_info_temp.getItem());
                    new_order_item.setItemCurrentPrice(cart_info_temp.getItem().getItemPrice());
                    new_order_item.setOrder(new_order);
                    ModelHelper.createSingle(new_order_item);
                }
            }

            CartHelper.cleanCart(current_user);
            MsgHelper.setMsg(session, "已经为您创建订单。");
            return "redirect: /buyer/orders";
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
     *            订单 ID
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/buyer/orders/detail", method =  RequestMethod.GET)
    public String detail(@RequestParam("id") String id,
                         Model model,
                         HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            int order_id = Integer.valueOf(id);

            OrdersEntity order = (OrdersEntity) ModelHelper.getSingle(OrdersEntity.class, Restrictions.and(Restrictions.eq("id", order_id),
                    Restrictions.eq("user", current_user)));

            model.addAttribute("Order", order);

            return "buyer/orders/detail";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
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
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/buyer/orders/confirm", method =  RequestMethod.POST)
    public String confirm(@RequestParam("id") String id,
                          @RequestParam(value = "page", defaultValue = "1") String page,
                          Model model,
                          HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            int order_id = Integer.valueOf(id);

            OrdersEntity order = (OrdersEntity) ModelHelper.getSingle(OrdersEntity.class, Restrictions.and(Restrictions.eq("id", order_id),
                    Restrictions.eq("user", current_user),
                    Restrictions.eq("status", OrdersEntity.STATUS_RECEIVE_PENDING)));

            order.setStatus(OrdersEntity.STATUS_RANK_PENDING);

            ModelHelper.updateSingle(order);

            MsgHelper.setMsg(session, "订单确认成功，欢迎您来评价一下哦！");
            return "redirect: /buyer/orders?page=" + page;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
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
     * @param session
     *            线程对象，用于存放临时信息
     * @param request
     *            请求对象，用于动态获取评论信息
     */
    @RequestMapping(value = "/buyer/orders/rank", method =  RequestMethod.POST)
    public String rank(@RequestParam("id") String id,
                       @RequestParam(value = "page", defaultValue = "1") String page,
                       Model model,
                       HttpSession session,
                       HttpServletRequest request) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            int order_id = Integer.valueOf(id);

            OrdersEntity order = (OrdersEntity) ModelHelper.getSingle(OrdersEntity.class, Restrictions.and(Restrictions.eq("id", order_id),
                    Restrictions.eq("user", current_user),
                    Restrictions.eq("status", OrdersEntity.STATUS_RANK_PENDING)));

            Iterator<OrderItemsEntity> items_iterator = order.getOrderItems().iterator();

            //TODO: 失败处理
            while(items_iterator.hasNext()) {
                OrderItemsEntity order_item = items_iterator.next();
                ItemsEntity item = order_item.getItem();

                Byte rank_number = Byte.valueOf(request.getParameter("rateing_number_" + item.getId()));
                String rank_text = request.getParameter("rateing_text_" + item.getId());

                ItemRanksEntity new_rank_info = new ItemRanksEntity();

                new_rank_info.setItem(item);
                new_rank_info.setOrder(order);

                new_rank_info.setRankScore(rank_number);
                new_rank_info.setRankDetail(rank_text);

                ModelHelper.createSingle(new_rank_info);
            }

            order.setStatus(OrdersEntity.STATUS_FINISHED);
            ModelHelper.updateSingle(order);

            MsgHelper.setMsg(session, "订单 评价成功，感谢！");
            return "redirect: /buyer/orders?page=" + page;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }
}
