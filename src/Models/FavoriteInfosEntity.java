package Models;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "favorite_infos", schema = "etrade_system", catalog = "")
public class FavoriteInfosEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private UsersEntity user;
    private ItemsEntity item;

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName="id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id", referencedColumnName="id")
    public ItemsEntity getItem() {
        return item;
    }

    public void setItem(ItemsEntity item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteInfosEntity that = (FavoriteInfosEntity) o;
        return user.equals(that.user) &&
                item.equals(that.item);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user.getId(), item.getId());
    }
}
