package org.example.outsourcing_project.domain.menu.controller;

import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.service.MenuService;
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
@RequestMapping("/shops/{shopid}")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/menus")
	public ResponseEntity<MenuCreateResponseDto> createMenu(
		@PathVariable Long shopid,
		@RequestBody @Valid MenuCreateRequestDto requestDto) {

		// TODO test를 위해 userId 하드코딩으로 나중에 수정 필요
		Long userid = 1L;
		MenuCreateResponseDto menuCreateResponseDto = menuService.createMenu(userid, shopid, requestDto);

		return new ResponseEntity<>(menuCreateResponseDto, HttpStatus.OK);
	}

	@PatchMapping("/menus/{menuid}")
	public ResponseEntity<MenuUpdateResponseDto> updateMenu(
		@PathVariable Long shopid,
		@PathVariable Long menuid,
		@RequestBody @Valid MenuUpdateRequestDto requestDto) {

		// TODO test를 위해 userId 하드코딩으로 나중에 수정 필요
		Long userid = 1L;
		MenuUpdateResponseDto menuUpdateResponseDto = menuService.updateMenu(userid, shopid, menuid, requestDto);

		return new ResponseEntity<>(menuUpdateResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/menus/{menuid}")
	public ResponseEntity<Void> deletMenu(
		@PathVariable Long shopid,
		@PathVariable Long menuid) {

		// TODO test를 위해 userId 하드코딩으로 나중에 수정 필요
		Long userid = 1L;
		menuService.deleteMenu(userid,shopid,menuid);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

