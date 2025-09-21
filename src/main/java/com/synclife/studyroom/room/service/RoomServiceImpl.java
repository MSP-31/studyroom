package com.synclife.studyroom.room.service;

import com.synclife.studyroom.common.exception.exceptions.CustomException;
import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import com.synclife.studyroom.room.dto.RoomRequestDto;
import com.synclife.studyroom.room.dto.RoomResponseDto;
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
     *
     * @param requestDto 방이름, 위치, 인원
     * @return
     */
    @Secured("ROLE_ADMIN")
    @Override
    public RoomResponseDto createRoom(RoomRequestDto requestDto) {
        // 인원수가 0보다 작거나 같다면 오류
        if (requestDto.getCapacity() <= 0) {
            throw new CustomException(ExceptionMessage.CAPACITY_MUST_BE_POSITIVE);
        }

        Room room = Room.builder()
                .roomName(requestDto.getRoomName())
                .location(requestDto.getLocation())
                .capacity(requestDto.getCapacity())
                .build();
        Room saveRoom = roomRepository.save(room);

        return new RoomResponseDto(saveRoom);
    }
}
