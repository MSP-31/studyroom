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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회의실을 예약하였습니다."),
            @ApiResponse(responseCode = "400", description = "요청 값이 유효하지 않습니다. (G002)"),
            @ApiResponse(responseCode = "404", description = "해당하는 회의실이 없습니다. (R002)"),
            @ApiResponse(responseCode = "404", description = "시작 시간과 끝 시간이 같습니다. (R003)"),
            @ApiResponse(responseCode = "404", description = "시작 시간이 끝 시간보다 늦을 수 없습니다. (R004)"),
            @ApiResponse(responseCode = "409", description = "해당 시간대에 이미 예약이 존재합니다. (R006)"),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다. (G001)")
    })
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "예약 취소 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값이 유효하지 않습니다. (G002)"),
            @ApiResponse(responseCode = "404", description = "해당하는 예약을 찾을 수 없습니다. (R005)"),
            @ApiResponse(responseCode = "403", description = "예약 취소 권한이 없습니다. (R007)"),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다. (G001)")
    })
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
