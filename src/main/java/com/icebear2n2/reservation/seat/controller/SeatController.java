package com.icebear2n2.reservation.seat.controller;

import com.icebear2n2.reservation.domain.request.SeatRequest;
import com.icebear2n2.reservation.domain.response.SeatResponse;
import com.icebear2n2.reservation.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@RequestBody SeatRequest seatRequest) {
        SeatResponse createdSeat = seatService.createSeat(seatRequest);
        return new ResponseEntity<>(createdSeat, HttpStatus.CREATED);
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable Long seatId) {
        SeatResponse seat = seatService.getSeatById(seatId);
        return new ResponseEntity<>(seat, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SeatResponse>> getAllSeats(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<SeatResponse> seats = seatService.getAllSeats(PageRequest.of(page, size));
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<SeatResponse> updateSeat(@PathVariable Long seatId, @RequestBody SeatRequest seatRequest) {
        SeatResponse updatedSeat = seatService.updateSeat(seatId, seatRequest);
        return new ResponseEntity<>(updatedSeat, HttpStatus.OK);
    }

    @DeleteMapping("/{seatId}")
    public ResponseEntity<String > deleteSeat(@PathVariable Long seatId) {
        seatService.deleteSeat(seatId);
        return ResponseEntity.ok("Seat with ID " + seatId + " deleted successfully.");
    }
}
