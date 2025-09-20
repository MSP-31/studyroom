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

    /**
     * (관리자) 방 생성 메서드
     * @param requestDto 방이름, 위치, 인원
     */
    @Secured("ROLE_ADMIN")
    @Override
    public void createRoom(RoomRequestDto requestDto) {

        if (requestDto.getCapacity() <= 0) {
            throw new RuntimeException("수용 인원은 0보다 커야 합니다.");
        }

        Room room = Room.builder()
                .roomName(requestDto.getRoomName())
                .location(requestDto.getLocation())
                .capacity(requestDto.getCapacity())
                .build();
        roomRepository.save(room);
    }
}
