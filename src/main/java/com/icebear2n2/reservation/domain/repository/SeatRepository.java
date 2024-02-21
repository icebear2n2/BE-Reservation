package com.icebear2n2.reservation.domain.repository;

import com.icebear2n2.reservation.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
