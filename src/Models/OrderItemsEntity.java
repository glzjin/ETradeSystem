package Models;

import com.sun.istack.internal.Nullable;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "order_items", schema = "etrade_system", catalog = "")
public class OrderItemsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private OrdersEntity order;
    private ItemsEntity item;
    private BigDecimal itemCurrentPrice;
    private int itemAmount;
    private ItemRanksEntity rank;

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public ItemsEntity getItem() {
        return item;
    }

    public void setItem(ItemsEntity item) {
        this.item = item;
    }

    @Basic
    @Column(name = "item_current_price")
    public BigDecimal getItemCurrentPrice() {
        return itemCurrentPrice;
    }

    public void setItemCurrentPrice(BigDecimal itemCurrentPrice) {
        this.itemCurrentPrice = itemCurrentPrice;
    }

    @Basic
    @Column(name = "item_amount")
    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @Nullable
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
            @JoinColumn(name = "order_id", referencedColumnName="order_id", insertable = false, updatable = false),
            @JoinColumn(name = "item_id", referencedColumnName="item_id", insertable = false, updatable = false)
    })
    public ItemRanksEntity getRank() {
        return rank;
    }

    public void setRank(ItemRanksEntity rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemsEntity that = (OrderItemsEntity) o;
        return order.equals(that.order) &&
                item.equals(that.item) &&
                itemAmount == that.itemAmount &&
                Objects.equals(itemCurrentPrice, that.itemCurrentPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(order.getId(), item.getId(), itemCurrentPrice, itemAmount);
    }
}
