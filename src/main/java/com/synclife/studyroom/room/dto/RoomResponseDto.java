package com.synclife.studyroom.room.dto;

import com.synclife.studyroom.room.entity.Room;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class  RoomResponseDto {
    private final Long id;

    private final String roomName;

    private final String location;

    private final Long capacity;

    public RoomResponseDto(Room room) {
        this.id = room.getId();
        this.roomName = room.getRoomName();
        this.location = room.getLocation();
        this.capacity = room.getCapacity();
    }
}
