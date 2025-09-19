package com.synclife.studyroom.room.dto;

import com.synclife.studyroom.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReservationRequestDto {
    private Long room_id;

    private LocalDateTime start_at;

    private LocalDateTime end_at;
}
