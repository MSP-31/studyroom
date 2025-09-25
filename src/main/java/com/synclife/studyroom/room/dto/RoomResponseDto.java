package com.synclife.studyroom.room.dto;

import com.synclife.studyroom.room.entity.Room;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회의실 정보 응답 DTO")
public record RoomResponseDto(
        @Schema(description = "회의실 ID", example = "1") Long id,
        @Schema(description = "회의실 이름", example = "A동 3층 회의실") String roomName,
        @Schema(description = "회의실 위치", example = "서울 강남구 테헤란로 123") String location,
        @Schema(description = "회의실 수용 인원", example = "10") Long capacity) {

    public static RoomResponseDto from(Room room) {
        return new RoomResponseDto(
                room.getId(),
                room.getRoomName(),
                room.getLocation(),
                room.getCapacity()
        );
    }
}
