package com.hotel.bookingservice.controller;

import com.hotel.bookingservice.entity.Booking;
import com.hotel.bookingservice.entity.BookingHistory;
import com.hotel.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings(@RequestParam(required = false) Long userId,
                                        @RequestParam(required = false) Long hotelId,
                                        @RequestParam(required = false) Booking.BookingStatus status) {
        if (userId != null) {
            return bookingService.getBookingsByUser(userId);
        } else if (hotelId != null) {
            return bookingService.getBookingsByHotel(hotelId);
        } else if (status != null) {
            return bookingService.getBookingsByStatus(status);
        } else {
            return bookingService.getAllBookings();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hotel/{hotelId}/date-range")
    public List<Booking> getBookingsByHotelAndDateRange(
            @PathVariable Long hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return bookingService.getBookingsByHotelAndDateRange(hotelId, startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(createdBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id,
                                                       @RequestBody Map<String, Object> statusUpdate) {
        try {
            Booking.BookingStatus status = Booking.BookingStatus.valueOf(
                    statusUpdate.get("status").toString());
            Long changedBy = Long.valueOf(statusUpdate.get("changedBy").toString());
            String reason = statusUpdate.get("reason").toString();

            Booking updatedBooking = bookingService.updateBookingStatus(id, status, changedBy, reason);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id,
                                              @RequestBody Map<String, Object> cancelRequest) {
        try {
            Long changedBy = Long.valueOf(cancelRequest.get("changedBy").toString());
            String reason = cancelRequest.get("reason").toString();

            bookingService.cancelBooking(id, changedBy, reason);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/history")
    public List<BookingHistory> getBookingHistory(@PathVariable Long id) {
        return bookingService.getBookingHistory(id);
    }

    @GetMapping("/room/{roomId}/availability")
    public ResponseEntity<Map<String, Boolean>> checkRoomAvailability(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        boolean available = bookingService.isRoomAvailable(roomId, checkIn, checkOut);
        return ResponseEntity.ok(Map.of("available", available));
    }
}