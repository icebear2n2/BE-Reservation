package com.icebear2n2.reservation.domain.repository;

import com.icebear2n2.reservation.domain.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // 출발 시간이 빠른 순으로 항공편 조회
    Page<Flight> findAllByOrderByDepartureTimeAsc(PageRequest pageRequest);

    // 출발 시간이 느린 순으로 항공편 조회
    Page<Flight> findAllByOrderByDepartureTimeDesc(PageRequest pageRequest);

    // 특정 날짜에 출발하는 항공편 조회
    Page<Flight> findByDepartureDate(LocalDate date, PageRequest pageRequest);
}
