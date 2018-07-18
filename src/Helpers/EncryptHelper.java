package Helpers;

import jodd.util.BCrypt;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptHelper {
    /**
     * 对字符串md5加密
     *
     * @param password
     * @return
     */
    public static String getMD5Str(String password){
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Bcrypt 加密
     * @param password
     * @return hash 后的字符串
     */
    public static String getBCryptStr(String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        return hashed;
    }

    /**
     * 验证 Bcrypt  加密的密码
     * @param password
     * @param hash
     * @return 验证是否成功
     */
    public static Boolean verifyBCryptStr(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    /**
     * 外部访问方法，获取加密之后的方法
     * @param password
     * @param salt
     * @return 加密后的密码
     */
    public static String getEncryptedStr(String password, String salt) {
        String md5 = EncryptHelper.getMD5Str(password + salt);

        String hashed = EncryptHelper.getBCryptStr(md5);

        return hashed;
    }

    /**
     * 外部访问方法，验证加密之后的密码
     * @param password
     * @param salt
     * @param hash
     * @return 验证是否成功
     */
    public static Boolean verifyEncryptedStr(String password, String salt, String hash) {
        String md5 = EncryptHelper.getMD5Str(password + salt);

        return EncryptHelper.verifyBCryptStr(md5, hash);
    }
}
