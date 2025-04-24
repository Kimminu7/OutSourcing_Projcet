package org.example.outsourcing_project.domain.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Getter
@Embeddable
@NoArgsConstructor
public class OperatingHours {

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    public OperatingHours(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
    public void updateOpenTime(LocalTime openTime){
        if (openTime==null){
            this.openTime=openTime;
        }

    }
    public void updateCloseTime(LocalTime closeTime){
        if (closeTime==null){
            this.closeTime=closeTime;
        }

    }

}