package org.example.outsourcing_project.domain.menu.entity;

import java.util.ArrayList;
import java.util.List;

import org.example.outsourcing_project.common.baseEntity.BaseEntity;
import org.example.outsourcing_project.common.category.Category;
import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuUpdateRequestDto;
import org.example.outsourcing_project.domain.shop.entity.Shop;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;

	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(nullable = false,length = 100)
	private String name;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private Boolean status = true;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuOption> menuOptions = new ArrayList<>();

	public Menu(Shop shop, MenuCreateRequestDto dto) {

		this.shop = shop;
		this.category = dto.getCategory();
		this.name = dto.getName();
		this.price = dto.getPrice();
		this.status = true;

	}

	public void update(MenuUpdateRequestDto requestDto){
		this.name = requestDto.getName();
		this.price = requestDto.getPrice();
		this.category = requestDto.getCategory();
	}

	public void softDelete(){
		this.status = false;
	}

}

