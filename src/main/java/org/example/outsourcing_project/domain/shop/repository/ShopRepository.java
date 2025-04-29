package org.example.outsourcing_project.domain.shop.repository;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.exception.NotFoundShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface ShopRepository extends JpaRepository<Shop, Long> {

    // 조회용 - 보류, 영구폐점 모두 제외
    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.user " +
            "WHERE s.id = :shopId " +
            "AND s.shopStatus != 'CLOSED_PERMANENTLY' " +
            "AND s.shopStatus != 'PENDING'")
    Optional<Shop> findByIdWithUser(@Param("shopId") Long shopId);

    default Shop findByIdWithUserThrowException(Long shopId) {
        return findByIdWithUser(shopId)
                .orElseThrow(NotFoundShop::new);
    }

    //즐겨찾기용
    @Query("SELECT s FROM Shop s WHERE s.id = :shopId AND s.shopStatus != 'CLOSED_PERMANENTLY'")
    Optional<Shop> findActiveShopById(@Param("shopId")Long shopId);

    default Shop findActiveShopByIdThrowException(Long shopId) {
        return findActiveShopById(shopId).orElseThrow(RuntimeException::new);
    }

    //카테고리 조회
    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.user WHERE s.category = :category " +
            "AND s.shopStatus != 'CLOSED_PERMANENTLY' " +
            "AND s.shopStatus != 'PENDING'")
    List<Shop> findShopByCategory(@Param("category") Category category);
    // 전체 조회
    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.user " +
            "WHERE s.shopStatus != 'CLOSED_PERMANENTLY' " +
            "AND s.shopStatus != 'PENDING'")
    List<Shop> findShop();

    @Query("SELECT count(s.id) FROM Shop s WHERE s.user.id = :userId AND s.shopStatus != 'CLOSED_PERMANENTLY'")
    int countShopByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.user WHERE s.id = :shopId AND s.shopStatus != 'CLOSED_PERMANENTLY'")
    Optional<Shop> findActiveShopByIdWithUser(@Param("shopId") Long shopId);

}
