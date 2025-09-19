package com.synclife.studyroom.room.service;

import com.synclife.studyroom.room.dto.RoomRequestDto;
import com.synclife.studyroom.room.entity.Room;
import com.synclife.studyroom.room.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Secured("ROLE_ADMIN")
    @Override
    public void createRoom(RoomRequestDto roomRequestDto) {
        Room room = Room.builder()
                .roomName(roomRequestDto.getRoomName())
                .location(roomRequestDto.getLocation())
                .capacity(roomRequestDto.getCapacity())
                .build();
        roomRepository.save(room);
    }
}
