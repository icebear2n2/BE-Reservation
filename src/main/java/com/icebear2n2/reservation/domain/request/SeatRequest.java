package com.icebear2n2.reservation.domain.request;

import com.icebear2n2.reservation.domain.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatRequest {
    private String seatNumber;
    private String seatClass;

    public Seat toEntity() {
        return Seat.builder()
                .seatNumber(this.seatNumber)
                .seatClass(this.seatClass)
                .reserved(false)
                .build();
    }
}
