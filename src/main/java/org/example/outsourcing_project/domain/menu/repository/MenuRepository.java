package org.example.outsourcing_project.domain.menu.repository;

import java.util.List;
import java.util.Optional;

import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Optional<Menu> findByIdAndShopId(Long id, Long shopid);

	List<Menu> findByIdAndNameContaining(Long shopId, String name);

}
