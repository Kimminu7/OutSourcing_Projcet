package org.example.outsourcing_project.domain.shop.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.common.baseEntity.BaseEntity;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.DayOfWeek;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(nullable = false,length = 100)
    private String storeName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String storeNumber;

    @Column(nullable = false)
    private long minDeliveryPrice = 0L;

    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int stars=0;

    private LocalDateTime status;

    @ElementCollection(targetClass = ShopDayOfWeek.class)
    @CollectionTable(name = "shop_closed_days", joinColumns = @JoinColumn(name = "shop_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private List<ShopDayOfWeek> closedDays;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Shop(User user, ShopRequestDto shopRequestDto){
        this.user = user;
        this.storeName = shopRequestDto.getStoreName();
        this.storeNumber = shopRequestDto.getStoreNumber();
        this.address = shopRequestDto.getAddress();
        this.category = shopRequestDto.getCategory();
        this.closedDays = shopRequestDto.getClosedDays();
        this.startTime = shopRequestDto.getStartTime();
        this.endTime = shopRequestDto.getEndTime();
        this.minDeliveryPrice = shopRequestDto.getMinDeliveryPrice();
    }

    public Shop update(ShopPatchRequestDto shopPatchRequestDto){
        if (shopPatchRequestDto.getStoreNumber() != null){
            this.storeNumber = shopPatchRequestDto.getStoreNumber();
        }
        if (shopPatchRequestDto.getEndTime() != null){
            this.endTime = shopPatchRequestDto.getEndTime();
        }
        if (shopPatchRequestDto.getMinDeliveryPrice() != null){
            this.minDeliveryPrice = shopPatchRequestDto.getMinDeliveryPrice();
        }
        if (shopPatchRequestDto.getStartTime() != null){
            this.startTime = shopPatchRequestDto.getStartTime();
        }

        setUpdatedAt(LocalDateTime.now());
        return this;
    }

}
