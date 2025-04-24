package org.example.outsourcing_project.domain.menu.dto.response;

import org.example.outsourcing_project.common.category.Category;
import org.example.outsourcing_project.domain.menu.entity.Menu;

import lombok.Getter;

@Getter
public class MenuUpdateResponseDto {

	// 똑같이 createDto와 똑같음... 리팩토링 여쭈어볼것

	private Long menuId;
	private Long shopId;
	private String name;
	private int price;
	private Category category;
	private Boolean status;

	public MenuUpdateResponseDto(Menu menu) {
		this.menuId = menu.getId();
		this.shopId = menu.getShop().getShopId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.category = menu.getCategory();
		this.status = menu.getStatus();

	}

}
