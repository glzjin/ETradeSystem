package Helpers;

import Models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserHelper {

    /**
     * 获取当前用户
     * @param session
     * @return
     */
    public static UsersEntity getCurrentUser(HttpSession session) {
        return (UsersEntity) session.getAttribute("user");
    }

    /**
     * 设置当前用户
     * @param session
     * @param user
     * @return
     */
    public static void setCurrentUser(HttpSession session, UsersEntity user) {
        session.setAttribute("user", user);
    }

    /**
     * 编辑用户地址信息
     * @param session
     * @param user
     * @param user_real_name
     * @param user_phone
     * @param user_address
     * @return
     */
    public static UsersEntity editUserAddressInfo(HttpSession session, UsersEntity user, String user_real_name, String user_phone, String user_address) {
        try{
            user.setUserRealName(user_real_name);
            user.setUserPhone(user_phone);
            user.setUserAddress(user_address);
            ModelHelper.updateSingle(user);

            session.setAttribute("user", user);

            return user;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return null;
        }

    }
}
