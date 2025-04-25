package org.example.outsourcing_project.domain.menu.dto.response;

import org.example.outsourcing_project.domain.menu.entity.Menu;

import lombok.Getter;

@Getter
public class MenuSearchResponseDto {

	private Long menuId;
	private String menuName;
	private int price;

	public MenuSearchResponseDto(Menu menu) {
		this.menuId = menu.getId();
		this.menuName = menu.getName();
		this.price = menu.getPrice();
	}
}
