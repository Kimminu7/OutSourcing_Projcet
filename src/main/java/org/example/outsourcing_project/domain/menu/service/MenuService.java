package org.example.outsourcing_project.domain.menu.service;

import org.apache.catalina.User;
import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;

public interface MenuService {
	//
	MenuCreateResponseDto createMenu(Long userId,Long shopId,MenuCreateRequestDto menuCreateRequestDto);
}
