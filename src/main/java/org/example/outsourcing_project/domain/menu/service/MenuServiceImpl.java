package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.request.MenuUpdateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuUpdateResponseDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MenuServiceImpl implements MenuService{

	private final MenuRepository menuRepository;
	private final ShopRepository shopRepository;
	// private final UserRepository userRepository;

	@Override
	public MenuCreateResponseDto createMenu(Long userId,Long shopId,MenuCreateRequestDto requestDto) {

		// TODO 수정 필요
		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

		Menu saveMenu = new Menu(shop,requestDto);
		menuRepository.save(saveMenu);

		return new MenuCreateResponseDto(saveMenu);
	}

	@Override
	@Transactional
	public MenuUpdateResponseDto updateMenu(Long userId, Long shopId,Long menuId, MenuUpdateRequestDto requestDto) {

		// TODO 수정 필요
		Shop shop = shopRepository.findById(shopId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));


		Menu menu = menuRepository.findByIdAndShop_ShopId(menuId,shopId)
				.orElseThrow(()-> new IllegalArgumentException("메뉴가 존재하지 않습니다."));


		menu.update(requestDto);

		return new MenuUpdateResponseDto(menu);
	}

	@Override
	@Transactional
	public void deleteMenu(Long userid, Long shopid, Long menuid) {
		Shop shop = shopRepository.findById(shopid)
			.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 매장입니다."));

		Menu menu = menuRepository.findByIdAndShop_ShopId(menuid, shopid)
			.orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

		menu.softDelete();

	}
}
