package org.example.outsourcing_project.domain.menu.repository;

import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
