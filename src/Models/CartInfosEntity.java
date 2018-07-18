package Models;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "cart_infos", schema = "etrade_system", catalog = "")
public class CartInfosEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private UsersEntity user;
    private ItemsEntity item;
    private int itemAmount;

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
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
    @Column(name = "item_amount")
    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartInfosEntity that = (CartInfosEntity) o;
        return user.equals(that.user) &&
                item.equals(that.item) &&
                itemAmount == that.itemAmount;
    }

    @Override
    public int hashCode() {

        return Objects.hash(user.getId(), item.getId(), itemAmount);
    }
}
