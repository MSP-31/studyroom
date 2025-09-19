package com.synclife.studyroom.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long userId;

    @EqualsAndHashCode.Include
    @Column(name = "rooms_id")
    private Long roomsId;

    @Builder
    public ReservationId(Long userId, Long roomsId){
        this.userId = userId;
        this.roomsId = roomsId;
    }
}
