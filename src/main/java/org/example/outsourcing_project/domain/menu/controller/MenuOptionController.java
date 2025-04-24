package org.example.outsourcing_project.domain.menu.controller;

import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.entity.MenuOption;
import org.example.outsourcing_project.domain.menu.service.MenuOptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops/{shopId}/menus/{menuId}")
@RequiredArgsConstructor
public class MenuOptionController {

	private final MenuOptionService menuOptionService;

	Long userId = 1L;

	@PostMapping("/options")
	public ResponseEntity<MenuOptionResponseDto> saveOption(
		@PathVariable Long shopId,
		@PathVariable Long menuId,
		@RequestBody @Valid MenuOptionRequestDto dto) {

		MenuOptionResponseDto optionResponseDto = menuOptionService.createOption(shopId, menuId, dto);

		return new ResponseEntity<>(optionResponseDto, HttpStatus.OK);
	}

	@PatchMapping("/options/{optionId}")
	public ResponseEntity<MenuOptionUpdateResponseDto> updateMenuOption(
		@PathVariable Long shopId,
		@PathVariable Long menuId,
		@PathVariable Long optionId,
		@RequestBody @Valid MenuOptionUpdateRequestDto dto){

		MenuOptionUpdateResponseDto menuOptionUpdateResponseDto = menuOptionService.updateOption(shopId, menuId,
			optionId, dto);

		return new ResponseEntity<>(menuOptionUpdateResponseDto,HttpStatus.OK);
	}



}
