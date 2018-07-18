package Helpers;

import Models.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;

public class StatsHelper {
    /**
     * 获取用户数
     * @return
     */
    public static int getUsersCount() {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            int count = (int)Math.ceil(((Long) db_session.createCriteria(UsersEntity.class)
                    .setProjection(Projections.rowCount())
                    .uniqueResult()));

            return count;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取商品数
     * @param user 可null，返回指定卖家的商品数
     * @return
     */
    public static int getItemsCount(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(ItemsEntity.class);

            if(user != null) {
                criteria.add(Restrictions.eq("user", user));
            }

            int count = (int)Math.ceil(((Long) criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult()));

            return count;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取订单数
     * @param user
     * @return
     */
    public static int getOrdersCount(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(OrdersEntity.class, "orders");

            if(user != null) {
                if(user.getUserRoleType() == UsersEntity.USER_ROLE_TYPE_BUYER) {
                    criteria.add(Restrictions.eq("orders.user", user));
                } else {
                    criteria.add(Restrictions.eq("orders.sellUser", user));
                }
            }

            int count = (int)Math.ceil(((Long) criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult()));

            return count;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取评价数
     * @param user
     * @return
     */
    public static int getRankInfosCount(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(ItemRanksEntity.class, "ranks");

            if(user != null) {
                if(user.getUserRoleType() == UsersEntity.USER_ROLE_TYPE_BUYER) {
                    criteria.createAlias("ranks.order", "order");
                    criteria.add(Restrictions.eq("order.user", user));
                } else {
                    criteria.createAlias("ranks.item", "item");
                    criteria.add(Restrictions.eq("item.user", user));
                }
            }

            int count = (int)Math.ceil(((Long) criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult()));

            return count;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取购物车条目数
     * @param user
     * @return
     */
    public static int getCartInfosCount(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(CartInfosEntity.class, "ranks");

            criteria.add(Restrictions.eq("user", user));

            int count = (int)Math.ceil(((Long) criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult()));

            return count;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取收藏条目数
     * @param user
     * @return
     */
    public static int getFavoriteInfosCount(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(FavoriteInfosEntity.class, "ranks");

            criteria.add(Restrictions.eq("user", user));

            int count = (int)Math.ceil(((Long) criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult()));

            return count;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取总收入数
     * @param user
     * @return
     */
    public static Double getTotalIncome(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(OrdersEntity.class, "order");

            if(user != null) {
                criteria.add(Restrictions.eq("order.sellUser", user));
            }

            return ((BigDecimal)criteria
                    .setProjection(Projections.sum("order.price"))
                    .uniqueResult()).doubleValue();
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0.0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 获取总消费数
     * @param user
     * @return
     */
    public static Double getTotalConsume(UsersEntity user) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Criteria criteria = db_session.createCriteria(OrdersEntity.class, "order");

            criteria.add(Restrictions.eq("order.user", user));

            return ((BigDecimal)criteria
                    .setProjection(Projections.sum("order.price"))
                    .uniqueResult()).doubleValue();
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0.0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }
}
