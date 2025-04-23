package org.example.outsourcing_project.domain.menu.service;

import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.dto.response.MenuCreateResponseDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MenuServiceImpl implements MenuService{

	private final MenuRepository menuRepository;
	private final ShopRepository shopRepository;
	// private final UserRepository userRepository;

	@Override
	public MenuCreateResponseDto createMenu(Long userId,Long shopId,MenuCreateRequestDto requestDto) {

		// TODO shop 생성 후 수정 필요
		Shop shop = shopRepository.findById(shopId).
			orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

		Menu saveMenu = new Menu(shop,requestDto);
		menuRepository.save(saveMenu);

		return new MenuCreateResponseDto(saveMenu);
	}
}
