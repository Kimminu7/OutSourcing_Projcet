package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.entity.MenuOption;
import org.example.outsourcing_project.domain.menu.repository.MenuOptionRepository;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuOptionServiceImpl implements MenuOptionService {

	private final MenuOptionRepository menuOptionRepository;
	private final MenuRepository menuRepository;

	@Override
	public MenuOptionResponseDto createOption(Long shopId, Long menuId, MenuOptionRequestDto dto) {

		Menu menu = menuRepository.findByIdAndShop_ShopId(menuId,shopId)
			.orElseThrow(()-> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		MenuOption menuOption = new MenuOption(menu,dto);

		MenuOption saveMenuOption = menuOptionRepository.save(menuOption);

		return new MenuOptionResponseDto(saveMenuOption);
	}
}
