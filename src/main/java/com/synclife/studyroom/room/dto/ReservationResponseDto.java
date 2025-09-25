package com.synclife.studyroom.room.dto;

import com.synclife.studyroom.room.entity.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "회의실 예약 응답 DTO")
public record ReservationResponseDto(
        @Schema(description = "예약 ID", example = "1") Long reservationId,
        @Schema(description = "예약한 사용자 ID", example = "5") Long userId,
        @Schema(description = "회의실 ID", example = "1") Long roomId,
        @Schema(description = "회의실 이름", example = "A동 3층 회의실") String roomName,
        @Schema(description = "회의실 위치", example = "서울 강남구 테헤란로 123") String location,
        @Schema(description = "회의실 수용 인원", example = "10") Long capacity,
        @Schema(description = "예약 시작 시간 (ISO 8601 형식)", example = "2025-09-21T14:00:00") String startAt,
        @Schema(description = "예약 종료 시간 (ISO 8601 형식)", example = "2025-09-21T15:00:00") String endAt) {

    public static ReservationResponseDto of(Reservation reservation, ReservationRequestDto requestDto) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getRoom().getId(),
                reservation.getRoom().getRoomName(),
                reservation.getRoom().getLocation(),
                reservation.getRoom().getCapacity(),
                String.valueOf(requestDto.startAt()),
                String.valueOf(requestDto.endAt())
        );
    }
}
