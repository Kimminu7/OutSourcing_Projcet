package org.example.outsourcing_project.domain.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuSearchResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
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

public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final ShopRepository shopRepository;
	private final UserRepository userRepository;

	// 메뉴 저장
	@Override
	public MenuCreateResponseDto createMenu(Long userId, Long shopId, MenuCreateRequestDto requestDto) {

		User user = userRepository.findByIdOrElseThrow(userId);
		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

		validateOwner(user, shop);

		Menu saveMenu = new Menu(shop, requestDto);
		menuRepository.save(saveMenu);

		return new MenuCreateResponseDto(saveMenu);
	}

	@Override
	@Transactional
	public MenuUpdateResponseDto updateMenu(Long userId, Long shopId, Long menuId, MenuUpdateRequestDto requestDto) {

		User user = userRepository.findByIdOrElseThrow(userId);

		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

		validateOwner(user, shop);

		Menu menu = menuRepository.findByIdAndShopId(menuId, shopId)
			.orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		menu.update(requestDto);

		return new MenuUpdateResponseDto(menu);
	}

	@Override
	@Transactional
	public void deleteMenu(Long userId, Long shopId, Long menuId) {

		User user = userRepository.findByIdOrElseThrow(userId);

		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

		validateOwner(user, shop);

		Menu menu = menuRepository.findByIdAndShopId(menuId, shopId)
			.orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		if (menu.getStatus().equals(false)) {
			throw new IllegalArgumentException("이미 삭제된 메뉴입니다.");
		}

		menu.softDelete();

	}

	@Override
	public MenuResponseDto getMenuByShop(Long shopId, Long menuId) {

		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

		Menu menus = menuRepository.findByIdAndShopIdAndStatusTrue(menuId, shopId)
			.orElseThrow(()->new IllegalArgumentException("삭제된 메뉴는 조회할 수 없습니다."));

		return new MenuResponseDto(menus);
	}

	@Override
	public List<MenuSearchResponseDto> searchMenuByKeyword(Long shopId, String keyword) {

		List<Menu> searchMenu = menuRepository.findByShopIdAndNameContaining(shopId, keyword);

		List<MenuSearchResponseDto> responseList = new ArrayList<>();
		for (Menu menu : searchMenu) {
			if (menu.getStatus()) {
				responseList.add(new MenuSearchResponseDto(menu));
			}
		}
		return responseList;
	}

	private void validateOwner(User user, Shop shop) {
		if (!shop.getUser().getId().equals(user.getId()) || user.getRole() != UserRole.OWNER) {
			throw new IllegalArgumentException("권한이 없습니다.");
		}
	}
}
