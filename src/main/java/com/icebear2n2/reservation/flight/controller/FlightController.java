package com.icebear2n2.reservation.flight.controller;

import com.icebear2n2.reservation.domain.request.FlightRequest;
import com.icebear2n2.reservation.domain.response.CreateFlightResponse;
import com.icebear2n2.reservation.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    // 항공편 생성
    @PostMapping
    public ResponseEntity<CreateFlightResponse> createFlight(@RequestBody FlightRequest flightRequest) {
        CreateFlightResponse response = flightService.createFlight(flightRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 항공편 조회
    @GetMapping("/{flightId}")
    public ResponseEntity<CreateFlightResponse> getFlightById(@PathVariable Long flightId) {
        CreateFlightResponse response = flightService.getFlightById(flightId);
        return ResponseEntity.ok(response);
    }

    // 출발 빠른 시간 순으로 항공편 조회
    @GetMapping("/fastest")
    public ResponseEntity<Page<CreateFlightResponse>> getFlightsByFastestTime(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CreateFlightResponse> response = flightService.getFlightsByFastestTime(pageRequest);
        return ResponseEntity.ok(response);
    }

    // 출발 느린 시간 순으로 항공편 조회
    @GetMapping("/slowest")
    public ResponseEntity<Page<CreateFlightResponse>> getFlightsBySlowestTime(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CreateFlightResponse> response = flightService.getFlightsBySlowestTime(pageRequest);
        return ResponseEntity.ok(response);
    }

    // 날짜 별로 항공편 조회
    @GetMapping("/by-date")
    public ResponseEntity<Page<CreateFlightResponse>> getFlightsByDate(@RequestParam("date") LocalDate date,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CreateFlightResponse> response = flightService.getFlightsByDate(date, pageRequest);
        return ResponseEntity.ok(response);
    }

    // 모든 항공편 조회
    @GetMapping
    public ResponseEntity<Page<CreateFlightResponse>> getAllFlights(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CreateFlightResponse> response = flightService.getAllFlights(pageRequest);
        return ResponseEntity.ok(response);
    }

    // 항공편 업데이트
    @PutMapping("/{flightId}")
    public ResponseEntity<CreateFlightResponse> updateFlight(@PathVariable Long flightId,
                                                       @RequestBody FlightRequest flightRequest) {
        CreateFlightResponse response = flightService.updateFlight(flightId, flightRequest);
        return ResponseEntity.ok(response);
    }

    // 항공편 삭제
    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok("Flight with ID " + flightId + " deleted successfully.");
    }
}
