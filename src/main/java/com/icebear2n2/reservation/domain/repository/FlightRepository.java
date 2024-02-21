package com.icebear2n2.reservation.domain.repository;

import com.icebear2n2.reservation.domain.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
