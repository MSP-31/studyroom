package com.synclife.studyroom.room.entity;

import com.synclife.studyroom.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @EmbeddedId
    private ReservationId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("roomsId")
    @JoinColumn(name = "rooms_id", nullable = false)
    private Room room;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Builder
    public Reservation(User user, Room room, LocalDateTime startAt, LocalDateTime endAt){
        this.id = new ReservationId(user.getId(),room.getId());
        this.user = user;
        this.room = room;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
