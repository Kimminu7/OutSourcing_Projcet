package org.example.outsourcing_project.domain.favorite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.user.entity.User;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Favorites implements Serializable {
    @EmbeddedId
    private FavoritesId id;

    @ManyToOne
    @MapsId("shopId") // EmbeddedId 안 필드명과 매칭
    @JoinColumn(name = "shop_shop_id")
    private Shop shop;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_user_id")
    private User user;

    public static Favorites of(User user, Shop shop) {
        return new Favorites(new FavoritesId(user.getId(), shop.getId()), shop, user);
    }

    @Override
    public String toString() {
        return "Favorites{" +
            "id=" + id +
            ", shop=" + shop.getShopName() + // shop의 이름만 출력
            ", user=" + user.getName() + // user의 이름만 출력
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return Objects.equals(id, favorites.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
