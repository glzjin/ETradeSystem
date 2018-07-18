package Helpers;

import Models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PageHelper {
    static int PageSize = 10;

    /**
     * 进行分页处理
     * @param model_class
     * @param current_page
     * @return 分页List
     */
    public static List pagenation(Class model_class, int current_page) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);
            //每次最多
            criteria.setMaxResults(PageSize);

            //起始位置
            criteria.setFirstResult((current_page - 1) * PageSize);
            //列出所有查询结果
            List<UsersEntity> list = criteria.list();

            return list;
        }catch(Exception e) {
            return null;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 进行分页处理
     * @param model_class
     * @param current_page
     * @param restrictions
     * @return
     */
    public static List pagenation(Class model_class, int current_page, Criterion restrictions) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);
            //每次最多
            criteria.setMaxResults(PageSize);

            //有限制条件的话加上
            criteria.add(restrictions);

            //起始位置
            criteria.setFirstResult((current_page - 1) * PageSize);
            //列出所有查询结果
            List<UsersEntity> list = criteria.list();

            return list;
        }catch(Exception e) {
            return null;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 进行分页处理
     * @param model_class
     * @param current_page
     * @param restrictions
     * @param alias_map 映射名
     * @return
     */
    public static List pagenation(Class model_class, int current_page, Criterion restrictions, HashMap<String, String> alias_map) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);
            //每次最多
            criteria.setMaxResults(PageSize);

            //先映射名称
            Set<String> keyset = alias_map.keySet();
            Iterator<String> iterator = keyset.iterator();
            while(iterator.hasNext()) {
                String key = iterator.next();
                String value = alias_map.get(key);

                criteria.createAlias(key, value);
            }

            //有限制条件的话加上
            criteria.add(restrictions);

            //起始位置
            criteria.setFirstResult((current_page - 1) * PageSize);
            //列出所有查询结果
            List<UsersEntity> list = criteria.list();

            return list;
        }catch(Exception e) {
            return null;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 统计页数
     * @param model_class
     * @return 总页数
     */
    public static int pageCount(Class model_class) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);

            int total_page = (int)Math.ceil(((Long) (criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult())) / Double.valueOf(PageSize));
            return total_page;
        }catch(Exception e) {
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 统计页数
     * @param model_class
     * @param restrictions
     * @return
     */
    public static int pageCount(Class model_class, Criterion restrictions) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);

            //有限制条件的话加上
            criteria.add(restrictions);

            int total_page = (int)Math.ceil(((Long) (criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult())) / Double.valueOf(PageSize));
            return total_page;
        }catch(Exception e) {
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 统计页数
     * @param model_class
     * @param restrictions
     * @param alias_map
     * @return
     */
    public static int pageCount(Class model_class, Criterion restrictions, HashMap<String, String> alias_map) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);

            //先映射名称
            Set<String> keyset = alias_map.keySet();
            Iterator<String> iterator = keyset.iterator();
            while(iterator.hasNext()) {
                String key = iterator.next();
                String value = alias_map.get(key);

                criteria.createAlias(key, value);
            }

            //有限制条件的话加上
            criteria.add(restrictions);

            int total_page = (int)Math.ceil(((Long) (criteria
                    .setProjection(Projections.rowCount())
                    .uniqueResult())) / Double.valueOf(PageSize));
            return total_page;
        }catch(Exception e) {
            return 0;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }
}
