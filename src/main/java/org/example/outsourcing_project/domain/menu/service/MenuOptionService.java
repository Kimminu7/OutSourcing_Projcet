package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;

public interface MenuOptionService {
	MenuOptionResponseDto createOption(Long shopId, Long menuId, MenuOptionRequestDto dto);
}
