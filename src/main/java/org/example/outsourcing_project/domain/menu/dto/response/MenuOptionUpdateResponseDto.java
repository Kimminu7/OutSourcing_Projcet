package org.example.outsourcing_project.domain.menu.dto.response;

import org.example.outsourcing_project.domain.menu.entity.MenuOption;

import lombok.Getter;
@Getter

public class MenuOptionUpdateResponseDto {

	private Long shopId;
	private Long menuId;
	private Long optionId;
	private String options;
	private int price;
	private Boolean status;

	public MenuOptionUpdateResponseDto(MenuOption menuOption) {
		this.menuId = menuOption.getMenu().getId();
		this.shopId = menuOption.getMenu().getShop().getId();
		this.optionId = menuOption.getId();
		this.options = menuOption.getOptions();
		this.price = menuOption.getPrice();
		this.status = menuOption.isStatus();

	}

}

