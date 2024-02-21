package com.icebear2n2.reservation.domain.response;

import com.icebear2n2.reservation.domain.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private Long id;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int seatCapacity;
    private double price;
    private List<SeatResponse> seats;
    private List<FlightReservationResponse> reservations;

    public FlightResponse(Flight flight) {
        this.id = flight.getId();
        this.departure = flight.getDeparture();
        this.destination = flight.getDestination();
        this.departureTime = flight.getDepartureTime();
        this.arrivalTime = flight.getArrivalTime();
        this.seatCapacity = flight.getSeatCapacity();
        this.price = flight.getPrice();
        // 좌석과 예약 정보는 각각의 response 클래스를 이용하여 변환
        this.seats = SeatResponse.fromSeatList(flight.getSeats());
        this.reservations = FlightReservationResponse.fromReservationList(flight.getReservations());
    }
}
