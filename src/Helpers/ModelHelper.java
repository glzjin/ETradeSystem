package Helpers;

import Models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import java.util.List;

public class ModelHelper {
    /**
     * 获取单个对象
     * @param model_class 对象类名
     * @param restrictions 限制条件
     * @return 对象
     */
    public static Object getSingle(Class model_class, Criterion restrictions) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始条件
            Criteria criteria = db_session.createCriteria(model_class);

            //追加外部条件
            criteria.add(restrictions);

            //只拉取一条
            criteria.setMaxResults(1);

            return criteria.list().get(0);
        }catch(Exception e) {
            return null;
        } finally {
            db_session.close();
        }
    }

    /**
     * 更新单个对象
     * @param new_single_object
     * @return
     */
    public static Boolean updateSingle(Object new_single_object) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Transaction db_trans = db_session.beginTransaction();
            db_session.update(new_single_object);
            db_trans.commit();

            return true;
        }catch(Exception e) {
            return false;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 删除单个对象
     * @param new_single_object
     * @return
     */
    public static Boolean deleteingle(Object new_single_object) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Transaction db_trans = db_session.beginTransaction();
            db_session.delete(new_single_object);
            db_trans.commit();

            return true;
        }catch(Exception e) {
            return false;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 添加单个对象
     * @param new_single_object
     * @return
     */
    public static Boolean createSingle(Object new_single_object) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            Transaction db_trans = db_session.beginTransaction();
            db_session.save(new_single_object);
            db_trans.commit();

            return true;
        }catch(Exception e) {
            return false;
        }finally {
            //用完这个 数据库的 Session  记得关了
            db_session.close();
        }
    }

    /**
     * 列出所有符合条件的对象
     * @param model_class
     * @param restrictions
     * @return
     */
    public static List all(Class model_class, Object restrictions) {
        //创建数据库操作线程
        Session db_session = DBHelper.getSession();

        try{
            //创建初始创建条件
            Criteria criteria = db_session.createCriteria(model_class);

            //有限制条件的话加上
            if(restrictions != null) {
                if(restrictions instanceof Criterion) {
                    criteria.add((Criterion) restrictions);
                }
            }

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
}
