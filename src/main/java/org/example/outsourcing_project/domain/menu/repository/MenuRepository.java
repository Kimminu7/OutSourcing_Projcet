package org.example.outsourcing_project.domain.menu.repository;

import java.util.List;
import java.util.Optional;

import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Optional<Menu> findByIdAndShopId(Long id, Long shopid);

	List<Menu> findByShopIdAndNameContaining(Long shopId, String name);

	Optional<Menu> findByIdAndShopIdAndStatusTrue(Long menuid, Long shopId);

	@Query("SELECT AVG(m.star) FROM Menu m WHERE m.shop.id = :shopId")
	Double calculateAverageStarByShopId(@Param("shopId") Long shopId);


}
