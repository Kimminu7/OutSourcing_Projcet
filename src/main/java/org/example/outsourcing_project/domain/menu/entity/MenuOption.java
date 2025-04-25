package org.example.outsourcing_project.domain.menu.entity;

import org.example.outsourcing_project.common.baseEntity.BaseEntity;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionUpdateRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class MenuOption extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@Column(nullable = false)
	private String options;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private boolean status = true;

	public MenuOption(Menu menu, MenuOptionRequestDto dto){
		this.menu = menu;
		this.options = dto.getOptions();
		this.price = dto.getPrice();
		this.status = true;
	}

	public void softDelete(){
		this.status = false;
	}

	public void update(MenuOptionUpdateRequestDto dto) {
		this.options = dto.getOptions();
		this.price = dto.getPrice();
	}
}
