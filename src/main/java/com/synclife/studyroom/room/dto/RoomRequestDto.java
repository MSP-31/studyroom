package com.synclife.studyroom.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회의실 등록 요청 DTO")
public record RoomRequestDto(
        @Schema(description = "회의실 이름", example = "A동 3층 회의실") String roomName,
        @Schema(description = "회의실 위치", example = "서울 강남구 테헤란로 123") String location,
        @Schema(description = "회의실 수용 인원", example = "10") Long capacity) {

}
