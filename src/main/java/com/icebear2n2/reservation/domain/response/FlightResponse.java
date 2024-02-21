package com.icebear2n2.reservation.domain.response;

import com.icebear2n2.reservation.domain.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private Long flightId;
    private String departure;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private int seatCapacity;
    private double price;
    private List<SeatResponse> seats;
    private List<FlightReservationResponse> reservations;

    public FlightResponse(Flight flight) {
        this.flightId = flight.getFlightId();
        this.departure = flight.getDeparture();
        this.destination = flight.getDestination();
        this.departureDate = flight.getDepartureDate();
        this.departureTime = flight.getDepartureTime();
        this.arrivalDate = flight.getArrivalDate();
        this.arrivalTime = flight.getArrivalTime();
        this.seatCapacity = flight.getSeatCapacity();
        this.price = flight.getPrice();

        // 좌석과 예약 정보는 각각의 response 클래스를 이용하여 변환
        this.seats = SeatResponse.fromSeatList(flight.getSeats());
        this.reservations = flight.getReservations() != null ? FlightReservationResponse.fromReservationList(flight.getReservations()) : null;
    }
}
