package org.example.outsourcing_project.domain.shop.entity;


import jakarta.persistence.*;
import org.example.outsourcing_project.common.baseEntity.BaseEntity;
import org.example.outsourcing_project.common.category.Category;
import org.example.outsourcing_project.common.category.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private long minDeliveryPrice = 0L;

    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "shop_closed_days", joinColumns = @JoinColumn(name = "shop_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private List<DayOfWeek> closedDays;

    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

}
