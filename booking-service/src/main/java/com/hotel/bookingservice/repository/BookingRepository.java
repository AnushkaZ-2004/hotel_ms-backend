package com.hotel.bookingservice.repository;

import com.hotel.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByHotelId(Long hotelId);
    List<Booking> findByBookingStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND " +
            "((b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn)) AND " +
            "b.bookingStatus NOT IN ('CANCELLED', 'CHECKED_OUT')")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);

    @Query("SELECT b FROM Booking b WHERE b.hotelId = :hotelId AND " +
            "b.checkInDate >= :startDate AND b.checkInDate <= :endDate")
    List<Booking> findBookingsByHotelAndDateRange(@Param("hotelId") Long hotelId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
}