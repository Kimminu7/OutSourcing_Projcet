package org.example.outsourcing_project.domain.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ShopResponseDto {
    private String storeName;
    private String address;
    private boolean open;
    private int star;
    private long minDeliverPrice;

}
