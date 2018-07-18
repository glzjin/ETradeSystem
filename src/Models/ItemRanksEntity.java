package Models;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "item_ranks", schema = "etrade_system", catalog = "")
public class ItemRanksEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private ItemsEntity item;
    private byte rankScore;
    private String rankDetail;
    private OrdersEntity order;

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id", referencedColumnName="id")
    public ItemsEntity getItem() {
        return item;
    }

    public void setItem(ItemsEntity item) {
        this.item = item;
    }

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName="id")
    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    @Basic
    @Column(name = "rank_score")
    public byte getRankScore() {
        return rankScore;
    }

    public void setRankScore(byte rankScore) {
        this.rankScore = rankScore;
    }

    @Basic
    @Column(name = "rank_detail")
    public String getRankDetail() {
        return rankDetail;
    }

    public void setRankDetail(String rankDetail) {
        this.rankDetail = rankDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRanksEntity that = (ItemRanksEntity) o;
        return item.equals(that.item) &&
                rankScore == that.rankScore &&
                order.equals(that.order) &&
                Objects.equals(rankDetail, that.rankDetail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(item.getId(), rankScore, rankDetail, order.getId());
    }
}
