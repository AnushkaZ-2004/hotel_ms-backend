package com.hotel.hotelservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    private Integer floor;

    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.AVAILABLE;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Constructors
    public Room() {}

    public Room(Hotel hotel, RoomType roomType, String roomNumber, Integer floor) {
        this.hotel = hotel;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.floor = floor;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // JPA Lifecycle methods
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Room Status Enum
    public enum RoomStatus {
        AVAILABLE("Available"),
        OCCUPIED("Occupied"),
        MAINTENANCE("Under Maintenance"),
        OUT_OF_ORDER("Out of Order");

        private final String displayName;

        RoomStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Utility methods
    public boolean isAvailable() {
        return this.status == RoomStatus.AVAILABLE && this.isActive;
    }

    public void makeAvailable() {
        this.status = RoomStatus.AVAILABLE;
    }

    public void occupy() {
        this.status = RoomStatus.OCCUPIED;
    }

    public void setMaintenance() {
        this.status = RoomStatus.MAINTENANCE;
    }

    public void setOutOfOrder() {
        this.status = RoomStatus.OUT_OF_ORDER;
    }

    // Business logic methods
    public String getFullRoomIdentifier() {
        return String.format("%s - Room %s (Floor %d)",
                hotel != null ? hotel.getName() : "Unknown Hotel",
                roomNumber,
                floor != null ? floor : 0);
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", floor=" + floor +
                ", status=" + status +
                ", isActive=" + isActive +
                '}';
    }

    // equals and hashCode based on business key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (hotel != null && room.hotel != null &&
                !hotel.getId().equals(room.hotel.getId())) return false;
        return roomNumber != null ? roomNumber.equals(room.roomNumber) : room.roomNumber == null;
    }

    @Override
    public int hashCode() {
        int result = hotel != null && hotel.getId() != null ? hotel.getId().hashCode() : 0;
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        return result;
    }
}