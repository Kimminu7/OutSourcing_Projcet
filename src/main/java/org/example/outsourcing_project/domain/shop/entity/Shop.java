package org.example.outsourcing_project.domain.shop.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.example.outsourcing_project.common.converter.DayOfWeekDeserializer;
import org.example.outsourcing_project.common.entity.BaseTimeEntity;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.user.entity.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "shops")
@SQLDelete(sql = "UPDATE shops SET shop_status = 'CLOSED_PERMANENTLY' WHERE shop_id = ?")//소프트
@SQLRestriction("shop_status != 'CLOSED_PERMANENTLY'")//where의 대안책
public class Shop extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(nullable = false, length = 100)
    private String shopName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String shopNumber;

    @Column(nullable = false)
    @Builder.Default
    private long minDeliveryPrice = 0L;

    @Embedded
    private OperatingHours operatingHours;

    @Column(nullable = false)
    @Setter
    @Builder.Default
    private double stars = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ShopStatus shopStatus = ShopStatus.CLOSED;

    @ElementCollection(targetClass = ShopDayOfWeek.class)
    @CollectionTable(name = "shop_closed_days", joinColumns = @JoinColumn(name = "shop_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    @JsonDeserialize(contentUsing = DayOfWeekDeserializer.class)
    @Builder.Default
    private List<ShopDayOfWeek> closedDays = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();

    public void update(ShopPatchRequestDto shopPatchRequestDto) {
        if (shopPatchRequestDto.getStoreNumber() != null) {
            this.shopNumber = shopPatchRequestDto.getStoreNumber();
        }
        if (shopPatchRequestDto.getMinDeliveryPrice() != null) {
            this.minDeliveryPrice = shopPatchRequestDto.getMinDeliveryPrice();
        }
        this.operatingHours.updateCloseTime(shopPatchRequestDto.getCloseTime());
        this.operatingHours.updateOpenTime(shopPatchRequestDto.getOpenTime());
    }

    //자동
    public ShopStatus calculateCurrentStatus(LocalTime now) {

        ShopDayOfWeek today = ShopDayOfWeek.of(LocalDate.now().getDayOfWeek().name());

        if (shopStatus.equals(ShopStatus.CLOSED_PERMANENTLY)) {
            return ShopStatus.CLOSED_PERMANENTLY;
        }
        if (shopStatus.equals(ShopStatus.PENDING)) {
            return ShopStatus.PENDING;
        }

        if (!getClosedDays().contains(today) &&
                now.isAfter(getOperatingHours().getOpenTime()) &&
                now.isBefore(getOperatingHours().getCloseTime())) {
            return ShopStatus.OPEN;
        }
        return ShopStatus.CLOSED;
    }

    //수동
    public void updateShopStatus(ShopStatus status) {
        this.shopStatus = status;
    }


}
