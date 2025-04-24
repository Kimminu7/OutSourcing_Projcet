package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuUpdateResponseDto;

public interface MenuService {
	//
	MenuCreateResponseDto createMenu(Long userId,Long shopId,MenuCreateRequestDto menuCreateRequestDto);

	MenuUpdateResponseDto updateMenu(Long userId, Long shopId,Long menuId, MenuUpdateRequestDto requestDto);

	void deleteMenu(Long userid, Long shopid, Long menuid);
}
