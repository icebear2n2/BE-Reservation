package com.icebear2n2.reservation.domain.repository;

import com.icebear2n2.reservation.domain.entity.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
}
