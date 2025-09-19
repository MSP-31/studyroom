package com.synclife.studyroom.room.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.room.dto.RoomRequestDto;
import com.synclife.studyroom.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<ResponseDto<Void>> createRoom(@RequestBody @Valid RoomRequestDto roomRequestDto){
        roomService.createRoom(roomRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "회의실 등록 성공",
                        null
                )
        );
    }
}
