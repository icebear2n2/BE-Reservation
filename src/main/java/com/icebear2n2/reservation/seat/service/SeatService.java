package com.icebear2n2.reservation.seat.service;

import com.icebear2n2.reservation.domain.entity.Seat;
import com.icebear2n2.reservation.domain.repository.SeatRepository;
import com.icebear2n2.reservation.domain.request.SeatRequest;
import com.icebear2n2.reservation.domain.response.SeatResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    // 좌석 생성
    public SeatResponse createSeat(SeatRequest seatRequest) {
        try {
            Seat seat = seatRequest.toEntity();

            Seat savedSeat = seatRepository.save(seat);
            return new SeatResponse(savedSeat);

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to create seat: " + ex.getMessage());
        }
    }

    // 좌석 조회
    public SeatResponse getSeatById(Long seatId) {
        try {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + seatId));
            return new SeatResponse(seat);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get seat: " + ex.getMessage());
        }
    }

    // 모든 좌석 조회
    public Page<SeatResponse> getAllSeats(PageRequest pageRequest) {
        try {
            Page<Seat> all = seatRepository.findAll(pageRequest);
            return all.map(SeatResponse::new);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get all seats: " + ex.getMessage());
        }
    }

    // 좌석 업데이트
    public SeatResponse updateSeat(Long seatId, SeatRequest seatRequest) {
        try {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + seatId));

            // 업데이트
            if (seatRequest.getSeatNumber() != null) {
                seat.setSeatNumber(seatRequest.getSeatNumber());
            }
            if (seatRequest.getSeatClass() != null) {
                seat.setSeatClass(seatRequest.getSeatClass());
            }

            Seat updatedSeat = seatRepository.save(seat);
            return new SeatResponse(updatedSeat);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to update seat: " + ex.getMessage());
        }
    }


    // 좌석 삭제
    public void deleteSeat(Long seatId) {
        if (!seatRepository.existsById(seatId)) {
            throw new EntityNotFoundException("Seat not found with id: " + seatId);
        }
        seatRepository.deleteById(seatId);
    }
}
