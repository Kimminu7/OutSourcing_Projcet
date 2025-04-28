package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuOptionUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuOptionUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.entity.MenuOption;
import org.example.outsourcing_project.domain.menu.repository.MenuOptionRepository;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuOptionServiceImpl implements MenuOptionService {

	private final MenuOptionRepository menuOptionRepository;
	private final MenuRepository menuRepository;
	private final UserRepository userRepository;
	private final ShopRepository shopRepository;

	// 메뉴 옵션 추가
	@Override
	public MenuOptionResponseDto createOption(Long shopId,Long userId, Long menuId, MenuOptionRequestDto dto) {

		User user = userRepository.findByIdOrElseThrow(userId);

		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SHOP));

		Menu menu = menuRepository.findByIdAndShopId(menuId,shopId)
			.orElseThrow(()-> new BaseException(ErrorCode.NOT_FOUND_MENU));

		validateOwner(user,shop);

		MenuOption menuOption = new MenuOption(menu,dto);

		MenuOption saveMenuOption = menuOptionRepository.save(menuOption);

		return new MenuOptionResponseDto(saveMenuOption);
	}

	// 메뉴 옵션 수정
	@Override
	@Transactional
	public MenuOptionUpdateResponseDto updateOption(Long shopId, Long userId, Long menuId, Long optionId, MenuOptionUpdateRequestDto dto) {

		User user = userRepository.findByIdOrElseThrow(userId);

		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SHOP));

		menuRepository.findByIdAndShopId(menuId,shopId)
			.orElseThrow(()-> new BaseException(ErrorCode.NOT_FOUND_MENU));

		MenuOption menuOption = menuOptionRepository.findById(optionId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_OPTION));

		validateOwner(user,shop);

		if (!menuOption.getMenu().getId().equals(menuId)) {
			throw new BaseException(ErrorCode.INVALID_MENU_OPTION_RELATION);
		}

		menuOption.update(dto);

		return new MenuOptionUpdateResponseDto(menuOption);
	}

	// 메뉴 옵션 삭제
	@Override
	@Transactional
	public void deleteOption(Long shopId, Long userId, Long menuId, Long optionId) {

		User user = userRepository.findByIdOrElseThrow(userId);

		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SHOP));

		Menu menu = menuRepository.findByIdAndShopId(menuId,shopId)
			.orElseThrow(()-> new BaseException(ErrorCode.NOT_FOUND_MENU));

		MenuOption menuOption = menuOptionRepository.findById(optionId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_OPTION));

		validateOwner(user,shop);

		if (!menuOption.getMenu().getId().equals(menu.getId())) {
			throw new BaseException(ErrorCode.INVALID_MENU_OPTION_RELATION);
		}

		if (!menuOption.isStatus()){
			throw new BaseException(ErrorCode.ALREADY_DELETED_OPTION);
		}

		menuOption.softDelete();
	}

	private void validateOwner(User user, Shop shop) {
		if (!shop.getUser().getId().equals(user.getId()) || user.getRole() != UserRole.OWNER) {
			throw new BaseException(ErrorCode.FORBIDDEN_ACCESS);
		}
	}
}
