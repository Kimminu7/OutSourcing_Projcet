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
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
import org.example.outsourcing_project.domain.user.entity.User;
import org.hibernate.annotations.SQLDelete;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "shop")
@SQLDelete(sql = "UPDATE shops SET shop_status = 'CLOSED_PERMANENTLY' WHERE shop_id = ?")//소프트

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
    private ShopStatus shopStatus = ShopStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ShopStatusAuth shopStatusAuth =ShopStatusAuth.AUTO;


    @ElementCollection(targetClass = ShopDayOfWeek.class)
    @CollectionTable(name = "shop_closed_days", joinColumns = @JoinColumn(name = "shop_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    @JsonDeserialize(contentUsing = DayOfWeekDeserializer.class)
    @Builder.Default
    private List<ShopDayOfWeek> closedDays = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.closedDays == null) {
            this.closedDays = new ArrayList<>();
        }
    }

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();


    //가장정보 업데이트
    //DTO 의존성 추후개선
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

    //가게 영업 상태
    public void updateShopStatus(ShopStatus status,ShopStatusAuth auth) {
        this.shopStatus = status;
        this.shopStatusAuth=auth;
    }

}
