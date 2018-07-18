package Controllers.Buyer;

import Helpers.*;
import Models.ItemsEntity;
import Models.UsersEntity;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BuyerItemsController {
    //默认页数
    static int PageSize = 10;

    /**
     * @RequestParam注解的作用是：根据参数名从URL中取得参数值
     * @param page
     *             页数
     * @param keyword
     *             关键字，可空
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象，用于存放临时信息
     */
    @RequestMapping(value = "/buyer/items", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page,
                        @RequestParam(value = "keyword", defaultValue = "") String keyword,
                        Model model,
                        HttpSession session) {
        try{
            //参数预处理
            Integer currentPage = Integer.valueOf(page);

            if(!keyword.equals("")) {
                //如果有关键词的话得带上关键词
                LogicalExpression restrictions = Restrictions.and(Restrictions.or(Restrictions.like("itemName", keyword, MatchMode.ANYWHERE),
                        Restrictions.like("itemDescription", keyword, MatchMode.ANYWHERE)), Restrictions.eq("status", ItemsEntity.STATUS_ON));
                List list = PageHelper.pagenation(ItemsEntity.class, currentPage, restrictions);

                model.addAttribute("List", list);

                //获取总记录数，计算总页面数，传递总页面数
                int total_page = PageHelper.pageCount(ItemsEntity.class, restrictions);
                model.addAttribute("totalPage", total_page);
            } else {
                SimpleExpression restrictions = Restrictions.eq("status", ItemsEntity.STATUS_ON);
                List list = PageHelper.pagenation(ItemsEntity.class, currentPage, restrictions);

                model.addAttribute("List", list);

                //获取总记录数，计算总页面数，传递总页面数
                int total_page = PageHelper.pageCount(ItemsEntity.class, restrictions);
                model.addAttribute("totalPage", total_page);
            }

            return "buyer/items/index";
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
     * @param model
     *            一个域对象，可用于存储数据值
     */
    @RequestMapping(value = "/buyer/items/detail", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") String id,
                            Model model) {
        try{
            //参数预处理
            Integer itemId = Integer.valueOf(id);

            ItemsEntity item = (ItemsEntity) ModelHelper.getSingle(ItemsEntity.class, Restrictions.eq("id", itemId));

            //给管理页面加上要编辑的信息
            model.addAttribute("Item", item);

            return "buyer/items/detail";
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
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/buyer/items/favor", method = RequestMethod.POST)
    public String favor(@RequestParam(value = "id") String id,
                            Model model,
                            HttpSession session) {
        try{
            //参数预处理
            Integer itemId = Integer.valueOf(id);

            ItemsEntity item = (ItemsEntity) ModelHelper.getSingle(ItemsEntity.class, Restrictions.eq("id", itemId));

            //获取当前用户
            UsersEntity user = (UsersEntity)session.getAttribute("user");
            Boolean has_favor = FavorHelper.addFavor(user, item);

            //给管理页面加上要编辑的信息
            model.addAttribute("Item", item);

            if(has_favor) {
                MsgHelper.setMsg(session, "商品ID:" + id + " 添加到收藏成功！");
            } else {
                MsgHelper.setMsg(session, "商品ID:" + id + "  取消收藏成功！");
            }

            return "redirect:/buyer/items/detail?id=" + id;
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
     * @param model
     *            一个域对象，可用于存储数据值
     * @param session
     *            线程对象
     */
    @RequestMapping(value = "/buyer/items/cart", method = RequestMethod.POST)
    public String cart(@RequestParam(value = "id") String id,
                           Model model,
                           HttpSession session) {
        try{
            //参数预处理
            Integer itemId = Integer.valueOf(id);

            ItemsEntity item = (ItemsEntity) ModelHelper.getSingle(ItemsEntity.class, Restrictions.eq("id", itemId));

            //获取当前用户
            UsersEntity user = (UsersEntity)session.getAttribute("user");
            CartHelper.addCart(user, item, true);

            //给管理页面加上要编辑的信息
            model.addAttribute("Item", item);

            MsgHelper.setMsg(session, "商品ID:" + id + " 添加到购物车成功！");

            return "redirect:/buyer/items/detail?id=" + id;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }
}
