package Models;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "orders", schema = "etrade_system", catalog = "")
public class OrdersEntity {
    public final static byte STATUS_PAY_PENDING = 0;
    public final static byte STATUS_DELIVER_PENDING = 1;
    public final static byte STATUS_RECEIVE_PENDING = 2;
    public final static byte STATUS_RANK_PENDING = 3;
    public final static byte STATUS_FINISHED = 4;

    private int id;
    private UsersEntity user;
    private Timestamp datetime;
    private byte status;
    private String deliverCompany;
    private String deliverOrderId;
    private BigDecimal price;
    private UsersEntity sellUser;
    private Collection<OrderItemsEntity> orderItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName="id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "deliver_company")
    public String getDeliverCompany() {
        return deliverCompany;
    }

    public void setDeliverCompany(String deliverCompany) {
        this.deliverCompany = deliverCompany;
    }

    @Basic
    @Column(name = "deliver_order_id")
    public String getDeliverOrderId() {
        return deliverOrderId;
    }

    public void setDeliverOrderId(String deliverOrderId) {
        this.deliverOrderId = deliverOrderId;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sell_user_id", referencedColumnName="id")
    public UsersEntity getSellUser() {
        return sellUser;
    }

    public void setSellUser(UsersEntity sellUser) {
        this.sellUser = sellUser;
    }

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName="id")
    @Fetch(FetchMode.SUBSELECT)
    public Collection<OrderItemsEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItemsEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public String statusText() {
        switch(this.status) {
            case OrdersEntity.STATUS_PAY_PENDING:
                return "等待付款";
            case OrdersEntity.STATUS_DELIVER_PENDING:
                return "等待发货";
            case OrdersEntity.STATUS_RECEIVE_PENDING:
                return "等待收货";
            case OrdersEntity.STATUS_RANK_PENDING:
                return "等待评价";
            case OrdersEntity.STATUS_FINISHED:
                return "完成";
            default:
                return "未知状态";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return id == that.id &&
                user.equals(that.user) &&
                status == that.status &&
                sellUser.equals(that.sellUser) &&
                Objects.equals(datetime, that.datetime) &&
                Objects.equals(deliverCompany, that.deliverCompany) &&
                Objects.equals(deliverOrderId, that.deliverOrderId) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user.getId(), datetime, status, deliverCompany, deliverOrderId, price, sellUser.getId());
    }
}
