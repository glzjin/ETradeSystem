package Controllers.Buyer;

import Helpers.*;
import Models.FavoriteInfosEntity;
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
public class BuyerFavoriteInfosController {
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
    @RequestMapping(value = "/buyer/favorite_infos", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page,
                        Model model,
                        HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            List list = PageHelper.pagenation(FavoriteInfosEntity.class, Integer.valueOf(page), Restrictions.eq("user", current_user));
            model.addAttribute("List", list);

            //获取总记录数，计算总页面数，传递总页面数
            int total_page = PageHelper.pageCount(FavoriteInfosEntity.class, Restrictions.eq("user", current_user));
            model.addAttribute("totalPage", total_page);

            return "buyer/favorite_infos/index";
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
    @RequestMapping(value = "/buyer/favorite_infos/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(value = "item_id") String id,
                         @RequestParam(value = "page", defaultValue = "1") String page,
                         Model model,
                         HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            ItemsEntity item = (ItemsEntity) ModelHelper.getSingle(ItemsEntity.class, Restrictions.eq("id", Integer.valueOf(id)));

            FavorHelper.delFavor(current_user, item);

            //返回信息
            MsgHelper.setMsg(session, "商品ID:" + id + "从收藏里删除成功！");
            return "redirect:/buyer/favorite_infos?page=" + page;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "buyer/fail";
        }
    }
}
