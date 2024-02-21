package com.icebear2n2.reservation.domain.response;

import com.icebear2n2.reservation.domain.entity.FlightReservation;
import com.icebear2n2.reservation.domain.entity.PaymentStatus;
import com.icebear2n2.reservation.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationResponse {
    private Long reservationId;
    private UserResponse user;
    private FlightResponse flight;
    private SeatResponse seat;
    private PaymentStatus paymentStatus;

    public FlightReservationResponse(FlightReservation reservation) {
        this.reservationId = reservation.getReservationId();
        this.user = new UserResponse(reservation.getUser());
        this.flight = new FlightResponse(reservation.getFlight());
        this.seat = new SeatResponse(reservation.getSeat());
        this.paymentStatus = reservation.getPaymentStatus();
    }

//    public static List<FlightReservationResponse> fromReservationList(List<FlightReservation> reservations) {
//        return reservations.stream()
//                .map(FlightReservationResponse::new)
//                .collect(Collectors.toList());
//    }
}
