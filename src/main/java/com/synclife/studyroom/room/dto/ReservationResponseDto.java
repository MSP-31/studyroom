package com.synclife.studyroom.room.dto;

import com.synclife.studyroom.room.entity.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@Schema(description = "회의실 예약 응답 DTO")
public class ReservationResponseDto {
    @Schema(description = "예약 ID", example = "1")
    private final Long reservationId;

    @Schema(description = "예약한 사용자 ID", example = "5")
    private final Long userId;

    @Schema(description = "회의실 ID", example = "1")
    private final Long roomId;

    @Schema(description = "회의실 이름", example = "A동 3층 회의실")
    private final String roomName;

    @Schema(description = "회의실 위치", example = "서울 강남구 테헤란로 123")
    private final String location;

    @Schema(description = "회의실 수용 인원", example = "10")
    private final Long capacity;

    @Schema(description = "예약 시작 시간 (ISO 8601 형식)", example = "2025-09-21T14:00:00")
    private final String startAt;

    @Schema(description = "예약 종료 시간 (ISO 8601 형식)", example = "2025-09-21T15:00:00")
    private final String endAt;

    public ReservationResponseDto(Reservation reservation, ReservationRequestDto requestDto) {
        this.reservationId = reservation.getId();
        this.userId = reservation.getUser().getId();
        this.roomId = reservation.getRoom().getId();
        this.roomName = reservation.getRoom().getRoomName();
        this.location = reservation.getRoom().getLocation();
        this.capacity = reservation.getRoom().getCapacity();
        this.startAt = String.valueOf(requestDto.getStartAt());
        this.endAt = String.valueOf(requestDto.getEndAt());
    }
}
