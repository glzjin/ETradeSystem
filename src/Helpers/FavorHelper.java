package Helpers;

import Models.FavoriteInfosEntity;
import Models.ItemsEntity;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;

public class FavorHelper {
    /**
     *
     * @param user
     * @param item
     * @return 操作完成是否还收藏
     */
    public static Boolean addFavor(UsersEntity user, ItemsEntity item) {
        if(FavorHelper.hasFavor(user, item)) {
            FavorHelper.delFavor(user, item);
            return false;
        } else {
            FavorHelper.setFavor(user, item);
            return true;
        }
    }

    /**
     *
     * @param user
     * @param item
     * @return 是否有收藏
     */
    public static Boolean hasFavor(UsersEntity user, ItemsEntity item) {
        if(FavorHelper.getFavor(user, item) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前收藏记录
     * @param user
     * @param item
     * @return 当前的收藏记录
     */
    public static FavoriteInfosEntity getFavor(UsersEntity user, ItemsEntity item) {
        try{
            FavoriteInfosEntity favor_info = (FavoriteInfosEntity)ModelHelper.getSingle(FavoriteInfosEntity.class, Restrictions.and(
                    Restrictions.eq("item", item),
                    Restrictions.eq("user", user)));

            return favor_info;
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 强行设置为已收藏
     * @param user
     * @param item
     * @return
     */
    public static Boolean setFavor(UsersEntity user, ItemsEntity item) {
        try{
            FavoriteInfosEntity new_record = new FavoriteInfosEntity();
            new_record.setItem(item);
            new_record.setUser(user);
            ModelHelper.createSingle(new_record);
            return true;
        }catch(Exception e) {
            //添加错误信息
            return false;
        }
    }

    /**
     * 删除当前的收藏记录
     * @param user
     * @param item
     * @return
     */
    public static Boolean delFavor(UsersEntity user, ItemsEntity item) {
        try{
            FavoriteInfosEntity favor_log = FavorHelper.getFavor(user, item);
            ModelHelper.deleteingle(favor_log);
            return true;
        }catch(Exception e) {
            //添加错误信息
            return false;
        }

    }
}
