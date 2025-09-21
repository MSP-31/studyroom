package com.synclife.studyroom.room.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDto {
    private final Long roomId;

    private final LocalDateTime startAt;

    private final LocalDateTime endAt;
}
