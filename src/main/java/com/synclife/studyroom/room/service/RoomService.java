package com.synclife.studyroom.room.service;

import com.synclife.studyroom.room.dto.RoomRequestDto;
import org.springframework.security.access.annotation.Secured;

public interface RoomService {
    @Secured("ROLE_ADMIN")
    void createRoom(RoomRequestDto roomRequestDto);
}
