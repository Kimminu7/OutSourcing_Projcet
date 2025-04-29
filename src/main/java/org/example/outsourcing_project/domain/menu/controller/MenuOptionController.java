package org.example.outsourcing_project.domain.menu.controller;

import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.service.MenuOptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	// 메뉴 옵션 등록
	@PostMapping("/options")
	public ResponseEntity<MenuOptionResponseDto> saveOption(
		@PathVariable Long shopId,
		@PathVariable Long menuId,
		@LoginUser Long userId,
		@RequestBody @Valid MenuOptionRequestDto dto) {

		MenuOptionResponseDto optionResponseDto = menuOptionService.createOption(shopId,userId, menuId, dto);

		return new ResponseEntity<>(optionResponseDto, HttpStatus.OK);
	}

	// 메뉴 옵션 수정
	@PatchMapping("/options/{optionId}")
	public ResponseEntity<MenuOptionUpdateResponseDto> updateOption(
		@LoginUser Long userId,
		@PathVariable Long shopId,
		@PathVariable Long menuId,
		@PathVariable Long optionId,
		@RequestBody @Valid MenuOptionUpdateRequestDto dto){

		MenuOptionUpdateResponseDto menuOptionUpdateResponseDto = menuOptionService.updateOption(shopId, userId, menuId,
			optionId, dto);

		return new ResponseEntity<>(menuOptionUpdateResponseDto,HttpStatus.OK);
	}

	// 메뉴 옵션 삭제
	@DeleteMapping("/options/{optionId}")
	public ResponseEntity<Void> deleteOption(
		@LoginUser Long userId,
		@PathVariable Long shopId,
		@PathVariable Long menuId,
		@PathVariable Long optionId){

		menuOptionService.deleteOption(shopId,userId,menuId,optionId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
