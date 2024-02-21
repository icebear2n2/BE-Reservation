package com.icebear2n2.reservation.flight.controller;

import com.icebear2n2.reservation.domain.request.FlightRequest;
import com.icebear2n2.reservation.domain.response.FlightResponse;
import com.icebear2n2.reservation.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    // 항공편 생성
    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(@RequestBody FlightRequest flightRequest) {
        FlightResponse response = flightService.createFlight(flightRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 항공편 조회
    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long flightId) {
        FlightResponse response = flightService.getFlightById(flightId);
        return ResponseEntity.ok(response);
    }

    // 모든 항공편 조회
    @GetMapping
    public ResponseEntity<Page<FlightResponse>> getAllFlights(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FlightResponse> response = flightService.getAllFlights(pageRequest);
        return ResponseEntity.ok(response);
    }

    // 항공편 업데이트
    @PutMapping("/{flightId}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long flightId,
                                                       @RequestBody FlightRequest flightRequest) {
        FlightResponse response = flightService.updateFlight(flightId, flightRequest);
        return ResponseEntity.ok(response);
    }

    // 항공편 삭제
    @DeleteMapping("/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }
}
