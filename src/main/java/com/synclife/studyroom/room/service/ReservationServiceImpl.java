package com.synclife.studyroom.room.service;

import com.synclife.studyroom.common.jwt.JwtService;
import com.synclife.studyroom.room.dto.ReservationRequestDto;
import com.synclife.studyroom.room.entity.Reservation;
import com.synclife.studyroom.room.entity.Room;
import com.synclife.studyroom.room.repository.ReservationRepository;
import com.synclife.studyroom.room.repository.RoomRepository;
import com.synclife.studyroom.user.entity.User;
import com.synclife.studyroom.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    /**
     * 회의실 예약 생성
     * @param requestDto 방아이디, 시작시간, 끝시간
     */
    @Override
    public void createReservation(ReservationRequestDto requestDto){
        User user = jwtService.getUser();
        Room room = roomRepository.findById(requestDto.getRoom_id())
                .orElseThrow(() -> new RuntimeException("해당하는 회의실이 없습니다."));

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .start_at(requestDto.getStart_at())
                .end_at(requestDto.getEnd_at())
                .build();
        reservationRepository.save(reservation);
        System.out.println("ok");
    }

    // @Override
    // public List<ReservationRequestDto> reservationView(LocalDate start_at, LocalDate end_at) {
    //
    // }
}
