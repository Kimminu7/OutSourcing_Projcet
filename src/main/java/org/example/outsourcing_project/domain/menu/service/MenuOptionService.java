package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionUpdateResponseDto;

import jakarta.validation.Valid;

public interface MenuOptionService {
	MenuOptionResponseDto createOption(Long shopId, Long menuId, MenuOptionRequestDto dto);

	MenuOptionUpdateResponseDto updateOption(Long shopId, Long menuId, Long optionId, MenuOptionUpdateRequestDto dto);

}
