package org.example.outsourcing_project.domain.shop.repository;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {


    default Shop findByIdThrowException(Long shopId){
        return findById(shopId).orElseThrow(RuntimeException::new);
    }

    List<Shop> findShopByCategory(Category category);


}
