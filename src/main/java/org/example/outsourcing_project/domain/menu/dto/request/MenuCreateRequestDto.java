package org.example.outsourcing_project.domain.menu.dto.request;

import org.checkerframework.checker.units.qual.C;
import org.example.outsourcing_project.common.enums.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuCreateRequestDto {

	@NotBlank (message = "메뉴이름은 비워둘 수 없습니다.")
	private String name;

	@NotNull (message = "가격은 비워둘 수 없습니다..")
	@Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
	private Integer price;

	private Category category;


}
