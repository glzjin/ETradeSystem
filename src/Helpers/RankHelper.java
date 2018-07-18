package Helpers;

import Models.ItemRanksEntity;
import Models.ItemsEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RankHelper {
    /**
     * 统计商品平均评价
     * @param item
     * @return
     */
    public static Double avrScore(ItemsEntity item) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Transaction tx = db_session.beginTransaction();
            //HQL
            Double avr = (Double)db_session.createQuery("select avg(ranks.rankScore) from ItemRanksEntity ranks where item = :item")
                    .setParameter("item", item)
                    .uniqueResult();
            tx.commit();

            if(avr == null) {
                return 0.0;
            }

            return avr;
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
     * 获取商品评价表
     * @param item
     * @param page
     * @return
     */
    public static List<ItemRanksEntity> rankList(ItemsEntity item, int page) {
        try{
            List<ItemRanksEntity> list = PageHelper.pagenation(ItemRanksEntity.class, page, Restrictions.eq("item", item));

            return list;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取商品评价总页数
     * @param item
     * @return
     */
    public static int rankListTotalPage(ItemsEntity item) {
        try{
            int total_page = PageHelper.pageCount(ItemRanksEntity.class, Restrictions.eq("item", item));

            return total_page;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return 0;
        }
    }
}
