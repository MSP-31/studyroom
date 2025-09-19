package com.synclife.studyroom.room.service;

import com.synclife.studyroom.room.dto.ReservationRequestDto;
import com.synclife.studyroom.room.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    void createReservation(ReservationRequestDto requestDto);

    List<ReservationResponseDto> getRoomsByDate(LocalDate date);
}
