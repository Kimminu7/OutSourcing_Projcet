package org.example.outsourcing_project.domain.menu.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionRequestDto {

	@NotBlank(message = "이름은 비워둘 수 없습니다.")
	private String options;

	@NotNull(message = "가격은 비워둘 수 없습니다.")
	@Min(value = 0, message = "가격은 0원 이상입니다.")
	private int price;

}
