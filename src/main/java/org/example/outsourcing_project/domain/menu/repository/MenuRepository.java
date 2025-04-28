package org.example.outsourcing_project.domain.menu.repository;

import java.util.List;
import java.util.Optional;

import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	@EntityGraph(attributePaths = "menuOptions")
	Optional<Menu> findByIdAndShop_id(Long id, Long shopid);

	List<Menu> findByShop_idAndNameContaining(Long shopId, String keyword);
}
