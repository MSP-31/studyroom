package com.synclife.studyroom.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "회의실 예약 요청 DTO")
public record ReservationRequestDto(
        @Schema(description = "예약할 회의실 ID", example = "1") Long roomId,
        @Schema(description = "예약 시작 시간 (ISO 8601 형식)", example = "2025-09-21T14:00:00") LocalDateTime startAt,
        @Schema(description = "예약 종료 시간 (ISO 8601 형식)", example = "2025-09-21T15:00:00") LocalDateTime endAt) {
}
