package Helpers;

import Models.CartInfosEntity;
import Models.ItemsEntity;
import Models.UsersEntity;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CartHelper {
    /**
     * 获取当前购物车记录
     * @param user
     * @param item
     * @param is_add
     * @return 当前的购物车记录
     */
    public static void addCart(UsersEntity user, ItemsEntity item, Boolean is_add) {
        try{
            CartInfosEntity cart_info = CartHelper.getCart(user, item);
            if(cart_info != null) {
                if(is_add) {
                    //添加
                    cart_info.setItemAmount(cart_info.getItemAmount() + 1);
                    ModelHelper.updateSingle(cart_info);
                } else {
                    if(cart_info.getItemAmount() == 1) {
                        //删除为0
                        ModelHelper.deleteingle(cart_info);
                    } else {
                        //往下减
                        cart_info.setItemAmount(cart_info.getItemAmount() - 1);
                        ModelHelper.updateSingle(cart_info);
                    }
                }

            } else {
                if(is_add) {
                    CartInfosEntity new_cart_info = new CartInfosEntity();
                    new_cart_info.setUser(user);
                    new_cart_info.setItem(item);
                    new_cart_info.setItemAmount(1);
                    ModelHelper.createSingle(new_cart_info);
                }
            }
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
        }
    }

    /**
     * 获取当前购物车记录
     * @param user
     * @param item
     * @return
     */
    public static CartInfosEntity getCart(UsersEntity user, ItemsEntity item) {
        try{
            CartInfosEntity cart_info = (CartInfosEntity) ModelHelper.getSingle(CartInfosEntity.class, Restrictions.and(
                    Restrictions.eq("user", user),
                    Restrictions.eq("item", item)
            ));

            if(cart_info != null) {
                return cart_info;
            } else {
                return null;
            }
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param user
     * @param item
     * @return 是否有加到购物车
     */
    public static Boolean hasCart(UsersEntity user, ItemsEntity item) {
        if(CartHelper.getCart(user, item) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个购物车记录
     * @param user
     * @param item
     * @return
     */
    public static void delCart(UsersEntity user, ItemsEntity item) {
        try{
            CartInfosEntity cart_info = CartHelper.getCart(user, item);

            if(cart_info != null) {
                ModelHelper.deleteingle(cart_info);
            }
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
        }

    }

    /**
     * 清空购物车内在库商品
     * @param user
     * @return
     */
    public static void cleanCart(UsersEntity user) {
        try{
            List<CartInfosEntity> list = ModelHelper.all(CartInfosEntity.class, Restrictions.eq("user", user));

            for(int i = 0; i < list.size(); i++) {
                CartInfosEntity cart_info = list.get(i);
                if(cart_info.getItem().getStatus() == ItemsEntity.STATUS_ON) {
                    ModelHelper.deleteingle(cart_info);
                }
            }
        }catch(Exception e) {
            //添加错误信息
            e.printStackTrace();
        }
    }

}
