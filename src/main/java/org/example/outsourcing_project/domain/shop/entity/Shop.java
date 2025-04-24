package org.example.outsourcing_project.domain.shop.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.outsourcing_project.common.baseEntity.BaseEntity;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.user.entity.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "shops")
@SQLDelete(sql = "UPDATE shops SET shop_status = 'CLOSED_PERMANENTLY' WHERE shop_id = ?")//소프트
@SQLRestriction("store_status != 'CLOSED_PERMANENTLY'")//where의 대안책
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(nullable = false,length = 100)
    private String shopName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String shopNumber;

    @Column(nullable = false)
    private long minDeliveryPrice = 0L;

    @Embedded
    private OperatingHours operatingHours;

    @Column(nullable = false)
    @Setter
    private double stars=0;

    @Enumerated(EnumType.STRING)
    private ShopStatus shopStatus;

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

    public Shop update(ShopPatchRequestDto shopPatchRequestDto){
        if (shopPatchRequestDto.getStoreNumber() != null){
            this.shopNumber = shopPatchRequestDto.getStoreNumber();
        }
        if (shopPatchRequestDto.getMinDeliveryPrice() != null){
            this.minDeliveryPrice = shopPatchRequestDto.getMinDeliveryPrice();
        }
        this.operatingHours.updateCloseTime(shopPatchRequestDto.getEndTime());
        this.operatingHours.updateOpenTime(shopPatchRequestDto.getStartTime());

        setUpdatedAt(LocalDateTime.now());
        return this;
    }
    public ShopStatus getCurrentShopStatus() {
        LocalTime now = LocalTime.now();
        ShopDayOfWeek today = ShopDayOfWeek.of(LocalDate.now().getDayOfWeek().name());
        if (shopStatus.equals(ShopStatus.CLOSED_PERMANENTLY)){
            return ShopStatus.CLOSED_PERMANENTLY;
        }
        if (!getClosedDays().contains(today) &&
                now.isAfter(getOperatingHours().getOpenTime()) &&
                now.isBefore(getOperatingHours().getCloseTime())){
            return ShopStatus.OPEN;
        }
        return ShopStatus.CLOSED;
    }

}
