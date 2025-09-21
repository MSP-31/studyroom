package com.synclife.studyroom.room.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.room.dto.ReservationRequestDto;
import com.synclife.studyroom.room.dto.ReservationResponseDto;
import com.synclife.studyroom.room.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "예약 API", description = "회의실 예약 생성 및 취소 기능을 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(
            summary = "회의실 예약 생성",
            description = "사용자가 회의실 예약을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<ResponseDto<ReservationResponseDto>> createReservation(
            @RequestBody @Valid ReservationRequestDto requestDto){
        ReservationResponseDto responseDto = reservationService.createReservation(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "회의실을 예약하였습니다.",
                        responseDto
                )
        );
    }

    @Operation(
            summary = "회의실 예약 취소",
            description = "예약 ID를 기반으로 예약을 취소합니다."
    )
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
