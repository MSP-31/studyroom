package com.synclife.studyroom.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Schema(description = "회의실 예약 요청 DTO")
public class ReservationRequestDto {
    @Schema(description = "예약할 회의실 ID", example = "1")
    private final Long roomId;

    @Schema(description = "예약 시작 시간 (ISO 8601 형식)", example = "2025-09-21T14:00:00")
    private final LocalDateTime startAt;

    @Schema(description = "예약 종료 시간 (ISO 8601 형식)", example = "2025-09-21T15:00:00")
    private final LocalDateTime endAt;
}
