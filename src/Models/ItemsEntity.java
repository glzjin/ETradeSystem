package Models;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "items", schema = "etrade_system", catalog = "")
public class ItemsEntity {
    public final static byte STATUS_ON = 1;
    public final static byte STATUS_OFF = 0;

    private int id;
    private String itemName;
    private BigDecimal itemPrice;
    private int itemSku;
    private int itemSold;
    private String itemImgUri;
    private String itemDescription;
    private UsersEntity user;
    private byte status;
    private Collection<ItemRanksEntity> itemRanks;

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
    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "item_price")
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Basic
    @Column(name = "item_sku")
    public int getItemSku() {
        return itemSku;
    }

    public void setItemSku(int itemSku) {
        this.itemSku = itemSku;
    }

    @Basic
    @Column(name = "item_sold")
    public int getItemSold() {
        return itemSold;
    }

    public void setItemSold(int itemSold) {
        this.itemSold = itemSold;
    }

    @Basic
    @Column(name = "item_img_uri")
    public String getItemImgUri() {
        return itemImgUri;
    }

    public void setItemImgUri(String itemImgUri) {
        this.itemImgUri = itemImgUri;
    }

    @Basic
    @Column(name = "item_description")
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName="id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName="id")
    @Fetch(FetchMode.SUBSELECT)
    public Collection<ItemRanksEntity> getItemRanks() {
        return itemRanks;
    }

    public void setItemRanks(Collection<ItemRanksEntity> itemRanks) {
        this.itemRanks = itemRanks;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String statusText() {
        switch(this.status) {
            case ItemsEntity.STATUS_OFF:
                return "下架";
            case ItemsEntity.STATUS_ON:
                return "上架";
            default:
                return "未知状态";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsEntity that = (ItemsEntity) o;
        return id == that.id &&
                itemSku == that.itemSku &&
                itemSold == that.itemSold &&
                user.equals(that.user) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(itemPrice, that.itemPrice) &&
                Objects.equals(itemDescription, that.itemDescription) &&
                status == that.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, itemName, itemPrice, itemSku, itemSold, itemDescription, user.getId());
    }
}
