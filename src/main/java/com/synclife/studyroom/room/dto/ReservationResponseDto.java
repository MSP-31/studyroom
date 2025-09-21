package com.synclife.studyroom.room.dto;

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
}
