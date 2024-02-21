package com.icebear2n2.reservation.domain.request;

import com.icebear2n2.reservation.domain.entity.Flight;
import com.icebear2n2.reservation.domain.entity.FlightReservation;
import com.icebear2n2.reservation.domain.entity.PaymentStatus;
import com.icebear2n2.reservation.domain.entity.User;
import com.icebear2n2.reservation.domain.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlightReservationRequest {
    private Long userId;
    private Long flightId;
    private String seatNumber;
    private PaymentStatus paymentStatus;

    public FlightReservation toEntity(User user, Flight flight, Seat seat) {
        return FlightReservation.builder()
                .user(user)
                .flight(flight)
                .seat(seat)
                .paymentStatus(paymentStatus)
                .build();
    }
}
