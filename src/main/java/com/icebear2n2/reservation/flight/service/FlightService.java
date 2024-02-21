package com.icebear2n2.reservation.flight.service;

import com.icebear2n2.reservation.domain.entity.Flight;
import com.icebear2n2.reservation.domain.entity.Seat;
import com.icebear2n2.reservation.domain.repository.FlightRepository;
import com.icebear2n2.reservation.domain.repository.SeatRepository;
import com.icebear2n2.reservation.domain.request.FlightRequest;
import com.icebear2n2.reservation.domain.response.CreateFlightResponse;
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
    public CreateFlightResponse createFlight(FlightRequest flightRequest) {
        try {
            Flight flight = flightRequest.toEntity();

            // 항공편을 먼저 저장
            Flight savedFlight = flightRepository.save(flight);

            // 저장된 항공편의 ID를 사용하여 좌석 생성 및 연결
            List<Seat> seats = generateSeats(flightRequest.getSeatCapacity(), savedFlight);

            // 저장된 좌석을 항공편에 설정
            savedFlight.setSeats(seats);

            // 저장된 항공편 반환
            return new CreateFlightResponse(savedFlight);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create flight: " + ex.getMessage());
        }
    }

    // 좌석 생성 및 항공편에 연결
    private List<Seat> generateSeats(int seatCapacity, Flight flight) {
        List<Seat> seats = new ArrayList<>();
        char[] seatClasses = {'B', 'E'}; // 비즈니스 클래스와 이코노미 클래스
        int businessCounter = 0; // 비즈니스 클래스 좌석 카운터
        for (int i = 1; i <= seatCapacity; i++) {
            // 좌석 번호 생성
            StringBuilder seatNumberBuilder = new StringBuilder();
            char seatClass;
            if (i <= 4) {
                seatClass = seatClasses[0]; // 1부터 4까지는 비즈니스 클래스로 설정
                businessCounter++;
            } else {
                seatClass = seatClasses[1]; // 이후 좌석은 이코노미 클래스로 설정
            }
            seatNumberBuilder.append(seatClass); // 좌석 클래스 추가
            seatNumberBuilder.append(i <= 4 ? businessCounter : (i - 4)); // 좌석 번호 추가

            Seat seat = Seat.builder()
                    .seatNumber(seatNumberBuilder.toString())
                    .seatClass(seatClass == 'B' ? "Business" : "Economy") // 좌석 클래스 설정
                    .flight(flight)
                    .reserved(false)
                    .build();
            seats.add(seat);
        }
        return seatRepository.saveAll(seats);
    }



    // 항공편 조회
    public CreateFlightResponse getFlightById(Long flightId) {
        try {
            Flight flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));
            return new CreateFlightResponse(flight);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get flight: " + ex.getMessage());
        }
    }

    // 모든 항공편 조회
    public Page<CreateFlightResponse> getAllFlights(PageRequest pageRequest) {
        try {
            Page<Flight> all = flightRepository.findAll(pageRequest);
            return all.map(CreateFlightResponse::new);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get all flights: " + ex.getMessage());
        }
    }

    // 항공편 업데이트
    public CreateFlightResponse updateFlight(Long flightId, FlightRequest flightRequest) {
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
            return new CreateFlightResponse(updateFlight);
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
