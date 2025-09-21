package com.synclife.studyroom.room.dto;

import com.synclife.studyroom.room.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ReservationResponseDto {
    private final Long roomId;

    private final String roomName;

    private final String location;

    private final Long capacity;

    private final String startAt;

    private final String endAt;

    public ReservationResponseDto(Reservation reservation, ReservationRequestDto requestDto) {
        this.roomId = reservation.getId();
        this.roomName = reservation.getRoom().getRoomName();
        this.location = reservation.getRoom().getLocation();
        this.capacity = reservation.getRoom().getCapacity();
        this.startAt = String.valueOf(requestDto.getStartAt());
        this.endAt = String.valueOf(requestDto.getEndAt());
    }
}
