package com.synclife.studyroom.room.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class  RoomResponseDto {
    private final Long id;

    private final String roomName;

    private final String location;

    private final Long capacity;
}
