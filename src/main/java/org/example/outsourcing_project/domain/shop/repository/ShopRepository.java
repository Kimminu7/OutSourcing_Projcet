package org.example.outsourcing_project.domain.shop.repository;

import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
}
