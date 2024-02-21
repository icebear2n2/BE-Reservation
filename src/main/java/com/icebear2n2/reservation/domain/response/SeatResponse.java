package com.icebear2n2.reservation.domain.response;

import com.icebear2n2.reservation.domain.entity.Seat;
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
public class SeatResponse {
    private Long seatId;
    private String seatNumber;
    private String seatClass;
    private boolean reserved;

    public SeatResponse(Seat seat) {
        this.seatId = seat.getSeatId();
        this.seatNumber = seat.getSeatNumber();
        this.seatClass = seat.getSeatClass();
        this.reserved = seat.isReserved();
    }

    public static List<SeatResponse> fromSeatList(List<Seat> seats) {
        return seats.stream()
                .map(SeatResponse::new)
                .collect(Collectors.toList());
    }
}
