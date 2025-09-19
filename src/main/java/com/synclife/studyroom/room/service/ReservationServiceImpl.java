package com.synclife.studyroom.room.service;

import com.synclife.studyroom.common.jwt.JwtService;
import com.synclife.studyroom.room.dto.ReservationRequestDto;
import com.synclife.studyroom.room.dto.ReservationResponseDto;
import com.synclife.studyroom.room.entity.Reservation;
import com.synclife.studyroom.room.entity.Room;
import com.synclife.studyroom.room.repository.ReservationRepository;
import com.synclife.studyroom.room.repository.RoomRepository;
import com.synclife.studyroom.user.entity.User;
import com.synclife.studyroom.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        Room room = roomRepository.findById(requestDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("해당하는 회의실이 없습니다."));

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .startAt(requestDto.getStartAt())
                .endAt(requestDto.getEndAt())
                .build();
        reservationRepository.save(reservation);
        System.out.println("ok");
    }

    @Override
    public List<ReservationResponseDto> getRoomsByDate(LocalDate date) {
        // 날짜 범위
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        
        List<Reservation> reservations = reservationRepository.findByStartAt(start,end);
        return toReservationResponseDto(reservations);
    }

    /**
     * 예약 엔티티들을 응답용 DTO로 변환하는 메서드
     * @param reservations 지정된 범위의 예약정보
     * @return 예약정보 DTO
     */
    private List<ReservationResponseDto> toReservationResponseDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> ReservationResponseDto.builder()
                        .roomId(reservation.getRoom().getId())
                        .roomName(reservation.getRoom().getRoomName())
                        .location(reservation.getRoom().getLocation())
                        .capacity(reservation.getRoom().getCapacity())
                        .startAt(reservation.getStartAt())
                        .endAt(reservation.getEndAt())
                        .build())
                .collect(Collectors.toList());
    }
}
