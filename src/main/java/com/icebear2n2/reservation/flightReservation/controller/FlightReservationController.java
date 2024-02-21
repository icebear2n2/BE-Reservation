package com.icebear2n2.reservation.flightReservation.controller;

import com.icebear2n2.reservation.domain.request.FlightReservationRequest;
import com.icebear2n2.reservation.domain.response.FlightReservationResponse;
import com.icebear2n2.reservation.flightReservation.service.FlightReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class FlightReservationController {
    private final FlightReservationService flightReservationService;

    @PostMapping
    public ResponseEntity<FlightReservationResponse> createReservation(@RequestBody FlightReservationRequest reservationRequest) {
        FlightReservationResponse response = flightReservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<FlightReservationResponse> getReservationById(@PathVariable Long reservationId) {
        FlightReservationResponse response = flightReservationService.getReservationById(reservationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FlightReservationResponse>> getAllReservations(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FlightReservationResponse> response = flightReservationService.getAllReservations(pageRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        flightReservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("Reservation with ID " + reservationId + " deleted successfully.");
    }
}
