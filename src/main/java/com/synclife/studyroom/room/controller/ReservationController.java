package com.synclife.studyroom.room.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.room.dto.ReservationRequestDto;
import com.synclife.studyroom.room.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createReservation(@RequestBody @Valid ReservationRequestDto requestDto){
        reservationService.createReservation(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "회의실 예약 성공",
                        null
                )
        );
    }
    
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteReservation(@RequestParam Long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "예약 취소 성공",
                        null
                )
        );
    }
            
}
