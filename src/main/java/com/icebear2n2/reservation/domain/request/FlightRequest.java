package com.icebear2n2.reservation.domain.request;

import com.icebear2n2.reservation.domain.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class FlightRequest {
    private String departure;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private int seatCapacity;
    private double price;

    public Flight toEntity() {
        return Flight.builder()
                .departure(this.departure)
                .destination(this.destination)
                .departureDate(this.departureDate)
                .departureTime(this.departureTime)
                .arrivalDate(this.arrivalDate)
                .arrivalTime(this.arrivalTime)
                .seatCapacity(this.seatCapacity)
                .price(this.price)
                .build();
    }
}
