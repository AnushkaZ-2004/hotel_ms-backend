package com.hotel.bookingservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_history")
public class BookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_from")
    private Booking.BookingStatus statusFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_to")
    private Booking.BookingStatus statusTo;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public BookingHistory() {}

    public BookingHistory(Long bookingId, Booking.BookingStatus statusFrom,
                          Booking.BookingStatus statusTo, Long changedBy, String changeReason) {
        this.bookingId = bookingId;
        this.statusFrom = statusFrom;
        this.statusTo = statusTo;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Booking.BookingStatus getStatusFrom() { return statusFrom; }
    public void setStatusFrom(Booking.BookingStatus statusFrom) { this.statusFrom = statusFrom; }

    public Booking.BookingStatus getStatusTo() { return statusTo; }
    public void setStatusTo(Booking.BookingStatus statusTo) { this.statusTo = statusTo; }

    public Long getChangedBy() { return changedBy; }
    public void setChangedBy(Long changedBy) { this.changedBy = changedBy; }

    public String getChangeReason() { return changeReason; }
    public void setChangeReason(String changeReason) { this.changeReason = changeReason; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}