package com.synclife.studyroom.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ReservationResponseDto {
    private final Long roomId;

    private final String roomName;

    private final String location;

    private final Long capacity;

    private final LocalDateTime startAt;

    private final LocalDateTime endAt;
}
