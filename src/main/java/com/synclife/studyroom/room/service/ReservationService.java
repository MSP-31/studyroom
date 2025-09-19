package com.synclife.studyroom.room.service;

import com.synclife.studyroom.room.dto.ReservationRequestDto;

public interface ReservationService {
    void createReservation(ReservationRequestDto requestDto);
}
