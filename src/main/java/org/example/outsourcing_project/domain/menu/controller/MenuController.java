package org.example.outsourcing_project.domain.menu.controller;

import java.util.List;

import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuSearchResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops/{shopId}")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	// 메뉴 등록
	@PostMapping("/menus")
	public ResponseEntity<MenuCreateResponseDto> createMenu(
		@PathVariable Long shopId,
		@LoginUser Long userId,
		@RequestBody @Valid MenuCreateRequestDto requestDto) {

		MenuCreateResponseDto menuCreateResponseDto = menuService.createMenu(userId, shopId, requestDto);

		return new ResponseEntity<>(menuCreateResponseDto, HttpStatus.OK);
	}

	// 메뉴 단건 조회
	@GetMapping("/menus/{menuId}")
	public ResponseEntity<MenuResponseDto> menuInfo(
		@PathVariable Long shopId,
		@LoginUser Long userId,
		@PathVariable Long menuId
	){

		MenuResponseDto menuResponseDto = menuService.getMenuByShop(userId, shopId, menuId);

		return new ResponseEntity<>(menuResponseDto,HttpStatus.OK);
	}

	// 메뉴 수정
	@PatchMapping("/menus/{menuId}")
	public ResponseEntity<MenuUpdateResponseDto> updateMenu(
		@PathVariable Long shopId,
		@PathVariable Long menuId,
		@LoginUser Long userId,
		@RequestBody @Valid MenuUpdateRequestDto requestDto) {

		MenuUpdateResponseDto menuUpdateResponseDto = menuService.updateMenu(userId, shopId, menuId, requestDto);

		return new ResponseEntity<>(menuUpdateResponseDto, HttpStatus.OK);
	}

	// 메뉴 삭제
	@DeleteMapping("/menus/{menuId}")
	public ResponseEntity<Void> deletMenu(
		@PathVariable Long shopId,
		@LoginUser Long userId,
		@PathVariable Long menuId) {

		menuService.deleteMenu(userId, shopId, menuId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/menus/search")
	public ResponseEntity<List<MenuSearchResponseDto>> searchMenu(
		@PathVariable Long shopId,
		@RequestParam String keyword
	){
		List<MenuSearchResponseDto> searchResult = menuService.searchMenuByKeyword(shopId,keyword);
		return new ResponseEntity<>(searchResult,HttpStatus.OK);
	}

}

