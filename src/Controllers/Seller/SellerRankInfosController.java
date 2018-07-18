package Controllers.Seller;

import Helpers.PageHelper;
import Helpers.UserHelper;
import Models.ItemRanksEntity;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class SellerRankInfosController {
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
    @RequestMapping(value = "/seller/rank_infos", method =  RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") String page, Model model, HttpSession session) {
        UsersEntity current_user = UserHelper.getCurrentUser(session);

        try{
            //别名
            HashMap<String, String> alias_map = new HashMap<String, String>();
            alias_map.put("item", "item");

            List list = PageHelper.pagenation(ItemRanksEntity.class, Integer.valueOf(page), Restrictions.eq("item.user", current_user), alias_map);

            model.addAttribute("List", list);

            //获取总记录数，计算总页面数，传递总页面数
            int total_page = PageHelper.pageCount(ItemRanksEntity.class, Restrictions.eq("item.user", current_user), alias_map);
            model.addAttribute("totalPage", total_page);

            return "seller/rank_infos/index";
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            model.addAttribute("error_msg", " 系统发生了错误！");
            return "seller/fail";
        }
    }
}
