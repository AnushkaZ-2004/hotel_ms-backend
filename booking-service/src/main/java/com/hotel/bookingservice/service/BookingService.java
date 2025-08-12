package com.hotel.bookingservice.service;

import com.hotel.bookingservice.entity.Booking;
import com.hotel.bookingservice.entity.BookingHistory;
import com.hotel.bookingservice.repository.BookingRepository;
import com.hotel.bookingservice.repository.BookingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getBookingsByHotel(Long hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }

    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByBookingStatus(status);
    }

    public List<Booking> getBookingsByHotelAndDateRange(Long hotelId, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findBookingsByHotelAndDateRange(hotelId, startDate, endDate);
    }

    public Booking createBooking(Booking booking) {
        // Check for conflicting bookings
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
                booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate());

        if (!conflictingBookings.isEmpty()) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        Booking savedBooking = bookingRepository.save(booking);

        // Create booking history entry
        BookingHistory history = new BookingHistory(
                savedBooking.getId(), null, savedBooking.getBookingStatus(),
                booking.getUserId(), "Booking created");
        bookingHistoryRepository.save(history);

        return savedBooking;
    }

    public Booking updateBookingStatus(Long id, Booking.BookingStatus newStatus, Long changedBy, String reason) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Booking.BookingStatus oldStatus = booking.getBookingStatus();
        booking.setBookingStatus(newStatus);

        Booking updatedBooking = bookingRepository.save(booking);

        // Create booking history entry
        BookingHistory history = new BookingHistory(
                booking.getId(), oldStatus, newStatus, changedBy, reason);
        bookingHistoryRepository.save(history);

        return updatedBooking;
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Check for conflicting bookings if dates are being changed
        if (!booking.getCheckInDate().equals(bookingDetails.getCheckInDate()) ||
                !booking.getCheckOutDate().equals(bookingDetails.getCheckOutDate()) ||
                !booking.getRoomId().equals(bookingDetails.getRoomId())) {

            List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
                    bookingDetails.getRoomId(), bookingDetails.getCheckInDate(),
                    bookingDetails.getCheckOutDate());

            // Remove current booking from conflicts
            conflictingBookings.removeIf(b -> b.getId().equals(booking.getId()));

            if (!conflictingBookings.isEmpty()) {
                throw new RuntimeException("Room is not available for the selected dates");
            }
        }

        booking.setCheckInDate(bookingDetails.getCheckInDate());
        booking.setCheckOutDate(bookingDetails.getCheckOutDate());
        booking.setAdults(bookingDetails.getAdults());
        booking.setChildren(bookingDetails.getChildren());
        booking.setTotalAmount(bookingDetails.getTotalAmount());
        booking.setSpecialRequests(bookingDetails.getSpecialRequests());
        booking.setPaymentStatus(bookingDetails.getPaymentStatus());

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long id, Long changedBy, String reason) {
        updateBookingStatus(id, Booking.BookingStatus.CANCELLED, changedBy, reason);
    }

    public List<BookingHistory> getBookingHistory(Long bookingId) {
        return bookingHistoryRepository.findByBookingIdOrderByCreatedAtDesc(bookingId);
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
        return conflictingBookings.isEmpty();
    }
}
