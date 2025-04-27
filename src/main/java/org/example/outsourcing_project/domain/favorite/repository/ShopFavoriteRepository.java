package org.example.outsourcing_project.domain.favorite.repository;

import org.example.outsourcing_project.domain.favorite.entity.Favorites;
import org.example.outsourcing_project.domain.favorite.entity.FavoritesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopFavoriteRepository extends JpaRepository<Favorites, FavoritesId> {
}
