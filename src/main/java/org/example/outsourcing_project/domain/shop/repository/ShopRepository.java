package org.example.outsourcing_project.domain.shop.repository;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {


    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.user " +
            "WHERE s.shopId = :shopId")
    Optional<Shop> findByIdWithUser(@Param("shopId") Long shopId);

    default Shop findByIdWithUserThrowException(Long shopId) {
        return findByIdWithUser(shopId)
                .orElseThrow(() -> new RuntimeException("해당 가게를 찾을 수 없습니다."));
    }


    default Shop findByIdThrowException(Long shopId){
        return findById(shopId).orElseThrow(RuntimeException::new);
    }

    List<Shop> findShopByCategory(Category category);

    @Query("SELECT count(s.shopId) FROM Shop s WHERE s.user.userId = :userId")
    int countShopByUserId(@Param("userId") Long userId);


}
