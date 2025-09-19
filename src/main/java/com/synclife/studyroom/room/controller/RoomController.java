package com.synclife.studyroom.room.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.room.dto.ReservationResponseDto;
import com.synclife.studyroom.room.dto.RoomRequestDto;
import com.synclife.studyroom.room.service.ReservationService;
import com.synclife.studyroom.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("rooms")
public class RoomController {
    private final RoomService roomService;
    private final ReservationService reservationService;

    @PostMapping
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

    @GetMapping
    public ResponseEntity<ResponseDto<List<ReservationResponseDto>>> createRoom(@RequestParam LocalDate date){
        List<ReservationResponseDto> reservationResponseDtos = reservationService.getRoomsByDate(date);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "해당 날짜의 모든 예약을 조회했습니다.",
                        reservationResponseDtos
                )
        );
    }
}
