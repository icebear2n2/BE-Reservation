package com.icebear2n2.reservation.flight.service;

import com.icebear2n2.reservation.domain.entity.Flight;
import com.icebear2n2.reservation.domain.entity.Seat;
import com.icebear2n2.reservation.domain.repository.FlightRepository;
import com.icebear2n2.reservation.domain.repository.SeatRepository;
import com.icebear2n2.reservation.domain.request.FlightRequest;
import com.icebear2n2.reservation.domain.response.FlightResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    // 항공편 생성
    public FlightResponse createFlight(FlightRequest flightRequest) {
        try {
            Flight flight = flightRequest.toEntity();

            // 좌석 생성 및 연결
            List<Seat> seats = generateSeats(flightRequest.getSeatCapacity(), flight);
            flight.setSeats(seats);

            Flight savedFlight = flightRepository.save(flight);
            return new FlightResponse(savedFlight);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to create flight: " + ex.getMessage());
        }
    }

    // 좌석 생성 및 항공편에 연결
    private List<Seat> generateSeats(int seatCapacity, Flight flight) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= seatCapacity; i++) {
            Seat seat = Seat.builder()
                    .seatNumber("Seat-" + i)
                    .seatClass("Economy") // 여기서는 기본값으로 Economy 로 설정
                    .flight(flight)
                    .reserved(false)
                    .build();
            seats.add(seat);
        }
        return seatRepository.saveAll(seats);
    }

    // 항공편 조회
    public FlightResponse getFlightById(Long flightId) {
        try {
            Flight flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));
            return new FlightResponse(flight);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get flight: " + ex.getMessage());
        }
    }

    // 모든 항공편 조회
    public Page<FlightResponse> getAllFlights(PageRequest pageRequest) {
        try {
            Page<Flight> all = flightRepository.findAll(pageRequest);
            return all.map(FlightResponse::new);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get all flights: " + ex.getMessage());
        }
    }

    // 항공편 업데이트
    public FlightResponse updateFlight(Long flightId, FlightRequest flightRequest) {
        try {
            Flight flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));

            // 업데이트
            if (flightRequest.getDeparture() != null) {
                flight.setDeparture(flightRequest.getDeparture());
            }
            if (flightRequest.getDestination() != null) {
                flight.setDestination(flightRequest.getDestination());
            }
            if (flightRequest.getDepartureDate() != null) {
                flight.setDepartureDate(flightRequest.getDepartureDate());
            }
            if (flightRequest.getDepartureTime() != null) {
                flight.setDepartureTime(flightRequest.getDepartureTime());
            }
            if (flightRequest.getArrivalDate() != null) {
                flight.setArrivalDate(flightRequest.getArrivalDate());
            }
            if (flightRequest.getArrivalTime() != null) {
                flight.setArrivalTime(flightRequest.getArrivalTime());
            }
            if (flightRequest.getSeatCapacity() > 0) {
                flight.setSeatCapacity(flightRequest.getSeatCapacity());
            }
            if (flightRequest.getPrice() > 0) {
                flight.setPrice(flightRequest.getPrice());
            }

            Flight updateFlight = flightRepository.save(flight);
            return new FlightResponse(updateFlight);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to update flight: " + ex.getMessage());
        }
    }


    // 항공편 삭제
    public void deleteFlight(Long flightId) {
        try {
            if (!flightRepository.existsById(flightId)) {
                throw new EntityNotFoundException("Flight not found with id: " + flightId);
            }
            flightRepository.deleteById(flightId);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to delete flight: " + ex.getMessage());
        }
    }
}
