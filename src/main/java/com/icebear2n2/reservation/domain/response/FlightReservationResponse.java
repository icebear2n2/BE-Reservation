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
    private Long flightReservationId;
    private UserResponse user;
    private FlightResponse flight;
    private List<SeatResponse> seats;
    private PaymentStatus paymentStatus;

    public FlightReservationResponse(FlightReservation reservation) {
        this.flightReservationId = reservation.getFlightReservationId();
        this.user = new UserResponse(reservation.getUser());
        this.flight = new FlightResponse(reservation.getFlight());
        this.seats = SeatResponse.fromSeatList(reservation.getSeats());
        this.paymentStatus = reservation.getPaymentStatus();
    }

    public static List<FlightReservationResponse> fromReservationList(List<FlightReservation> reservations) {
        return reservations.stream()
                .map(FlightReservationResponse::new)
                .collect(Collectors.toList());
    }
}
