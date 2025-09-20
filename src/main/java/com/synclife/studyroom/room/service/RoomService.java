package com.synclife.studyroom.room.service;

import com.synclife.studyroom.room.dto.RoomRequestDto;
import com.synclife.studyroom.room.dto.RoomResponseDto;
import org.springframework.security.access.annotation.Secured;

public interface RoomService {
    @Secured("ROLE_ADMIN")
    RoomResponseDto createRoom(RoomRequestDto roomRequestDto);
}
