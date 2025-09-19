package com.synclife.studyroom.room.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoomRequestDto {
    private String roomname;

    private String location;

    private Long capacity;
}
