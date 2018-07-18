package Models;

import Helpers.EncryptHelper;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "users", schema = "etrade_system")
public class UsersEntity {
    public static final byte USER_ROLE_TYPE_ADMIN = 1;
    public static final byte USER_ROLE_TYPE_SELLER_AUTHED = 2;
    public static final byte USER_ROLE_TYPE_BUYER = 3;
    public static final byte USER_ROLE_TYPE_SELLER_UNAUTH = 4;
    public static final byte USER_ROLE_TYPE_BANNED = 5;

    private int id;
    private String userName;
    private String password;
    private byte userRoleType;
    private String userRealName;
    private String userPhone;
    private String userAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void savePassword(String password) {
        this.password = EncryptHelper.getEncryptedStr(password, this.userName);
    }

    public Boolean verifyPassword(String password) {
        if(EncryptHelper.verifyEncryptedStr(password, this.userName, this.password)) {
            return true;
        } else {
            return false;
        }
    }

    @Basic
    @Column(name = "user_role_type")
    public byte getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(byte userRoleType) {
        this.userRoleType = userRoleType;
    }

    @Basic
    @Column(name = "user_real_name")
    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    @Basic
    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Basic
    @Column(name = "user_address")
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id &&
                userRoleType == that.userRoleType &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(userRealName, that.userRealName) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(userAddress, that.userAddress);
    }

    public String userRoleTypeName() {
        switch(this.userRoleType){
            case UsersEntity.USER_ROLE_TYPE_ADMIN:
                return "管理员";
            case UsersEntity.USER_ROLE_TYPE_BUYER:
                return "买家";
            case UsersEntity.USER_ROLE_TYPE_SELLER_AUTHED:
                return "已验证卖家";
            case UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH:
                return "未验证卖家";
            case UsersEntity.USER_ROLE_TYPE_BANNED:
                return "已封禁用户";
            default:
                return "未知";
        }
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, password, userRoleType, userRealName, userPhone, userAddress);
    }
}
