package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.entity.MenuOption;
import org.example.outsourcing_project.domain.menu.repository.MenuOptionRepository;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuOptionServiceImpl implements MenuOptionService {

	private final MenuOptionRepository menuOptionRepository;
	private final MenuRepository menuRepository;

	@Override
	public MenuOptionResponseDto createOption(Long shopId, Long menuId, MenuOptionRequestDto dto) {

		Menu menu = menuRepository.findByIdAndShop_id(menuId,shopId)
			.orElseThrow(()-> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		MenuOption menuOption = new MenuOption(menu,dto);

		MenuOption saveMenuOption = menuOptionRepository.save(menuOption);

		return new MenuOptionResponseDto(saveMenuOption);
	}

	@Override
	@Transactional
	public MenuOptionUpdateResponseDto updateOption(Long shopId, Long menuId, Long optionId, MenuOptionUpdateRequestDto dto) {

		Menu menu = menuRepository.findByIdAndShop_id(menuId,shopId)
			.orElseThrow(()-> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		MenuOption menuOption = menuOptionRepository.findById(optionId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

		if (!menuOption.getMenu().getId().equals(menu.getId())) {
			throw new IllegalArgumentException("이 메뉴에 없는 옵션입니다.");
		}

		menuOption.update(dto);

		return new MenuOptionUpdateResponseDto(menuOption);
	}

	@Override
	@Transactional
	public void deleteOption(Long shopId, Long menuId, Long optionId) {

		Menu menu = menuRepository.findByIdAndShop_id(menuId,shopId)
			.orElseThrow(()-> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		MenuOption menuOption = menuOptionRepository.findById(optionId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

		if (!menuOption.getMenu().getId().equals(menu.getId())) {
			throw new IllegalArgumentException("이 메뉴에 없는 옵션입니다.");
		}

		if (!menuOption.isStatus()){
			throw new IllegalArgumentException("이미 삭제된 옵션입니다.");
		}

		menuOption.softDelete();
	}
}
