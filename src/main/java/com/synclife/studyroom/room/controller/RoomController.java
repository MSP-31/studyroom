package com.synclife.studyroom.room.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.room.dto.ReservationResponseDto;
import com.synclife.studyroom.room.dto.RoomRequestDto;
import com.synclife.studyroom.room.dto.RoomResponseDto;
import com.synclife.studyroom.room.service.ReservationService;
import com.synclife.studyroom.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "회의실 API", description = "회의실 생성 및 예약 조회 관련 기능을 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("rooms")
public class RoomController {
    private final RoomService roomService;
    private final ReservationService reservationService;

    @Operation(
            summary = "회의실 생성",
            description = "관리자가 새로운 회의실을 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회의실을 등록하였습니다."),
            @ApiResponse(responseCode = "400", description = "요청 값이 유효하지 않습니다. (G002)"),
            @ApiResponse(responseCode = "400", description = "수용 인원은 0보다 커야 합니다. (R001)"),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다. (U004)"),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다. (G001)")
    })
    @PostMapping
    public ResponseEntity<ResponseDto<RoomResponseDto>> createRoom(@RequestBody @Valid RoomRequestDto roomRequestDto){
        RoomResponseDto responseDto = roomService.createRoom(roomRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "회의실을 등록하였습니다.",
                        responseDto
                )
        );
    }

    @Operation(
            summary = "예약 조회",
            description = "특정 날짜에 예약된 모든 회의실 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "해당 날짜의 모든 예약을 조회했습니다."),
            @ApiResponse(responseCode = "400", description = "요청 값이 유효하지 않습니다. (G002)"),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다. (G001)")
    })
    @Parameter(name = "date", description = "조회할 날짜 (yyyy-MM-dd)", required = true)
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
