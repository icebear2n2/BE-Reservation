package com.icebear2n2.reservation.flightReservation.service;

import com.icebear2n2.reservation.domain.entity.Flight;
import com.icebear2n2.reservation.domain.entity.FlightReservation;
import com.icebear2n2.reservation.domain.entity.Seat;
import com.icebear2n2.reservation.domain.entity.User;
import com.icebear2n2.reservation.domain.repository.FlightRepository;
import com.icebear2n2.reservation.domain.repository.FlightReservationRepository;
import com.icebear2n2.reservation.domain.repository.SeatRepository;
import com.icebear2n2.reservation.domain.repository.UserRepository;
import com.icebear2n2.reservation.domain.request.FlightReservationRequest;
import com.icebear2n2.reservation.domain.response.FlightReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightReservationService {
    private final FlightReservationRepository flightReservationRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    // 예약 생성
    public FlightReservationResponse createReservation(FlightReservationRequest reservationRequest) {
        try {
            User user = userRepository.findById(reservationRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + reservationRequest.getUserId()));
            Flight flight = flightRepository.findById(reservationRequest.getFlightId()).orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + reservationRequest.getFlightId()));
            Seat seat = seatRepository.findBySeatNumber(reservationRequest.getSeatNumber());

            // 좌석이 이미 예약되어 있는지 확인
            if (seat.isReserved()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already reserved.");
            }

            seat.setReserved(true);

            FlightReservation reservation = reservationRequest.toEntity(user, flight, seat);
            FlightReservation savedReservation = flightReservationRepository.save(reservation);
            return new FlightReservationResponse(savedReservation);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create reservation: " + ex.getMessage());
        }
    }

    // 예약 조회
    public FlightReservationResponse getReservationById(Long reservationId) {
        try {
            FlightReservation reservation = flightReservationRepository.findById(reservationId)
                    .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));
            return new FlightReservationResponse(reservation);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get reservation: " + ex.getMessage());
        }
    }

    // 모든 예약 조회
    public Page<FlightReservationResponse> getAllReservations(PageRequest pageRequest) {
        try {
            Page<FlightReservation> reservations = flightReservationRepository.findAll(pageRequest);
            return reservations.map(FlightReservationResponse::new);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to get all reservations: " + ex.getMessage());
        }
    }

    // 업데이트 로직은 일단 제외. 예약 취소 로직만 진행하자

    public void deleteReservation(Long reservationId) {
        try {
            if (!flightReservationRepository.existsById(reservationId)) {
                throw new EntityNotFoundException("Reservation not found with id: " + reservationId);
            }
            flightReservationRepository.deleteById(reservationId);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to delete reservation: " + ex.getMessage());
        }
    }
}
