package org.example.outsourcing_project.domain.menu.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.entity.MenuOption;

import lombok.Getter;

@Getter
public class MenuResponseDto {

	private Long shopId;
	private Long menuId;
	private String name;
	private int price;
	private Category category;
	private boolean status;
	private List<MenuOptionResponseDto> menuOptions;

	public MenuResponseDto(Menu menu) {

		this.shopId = menu.getShop().getId();
		this.menuId = menu.getId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.category = menu.getCategory();
		this.status = menu.getStatus();

		this.menuOptions = new ArrayList<>();
		for (MenuOption option : menu.getMenuOptions()){
			if (option.isStatus()){
				this.menuOptions.add(new MenuOptionResponseDto(option));
			}
		}
	}
}
