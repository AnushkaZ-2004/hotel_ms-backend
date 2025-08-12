package com.hotel.hotelservice.repository;

import com.hotel.hotelservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelIdAndIsActiveTrue(Long hotelId);
    List<Room> findByRoomTypeIdAndIsActiveTrue(Long roomTypeId);
    List<Room> findByStatusAndIsActiveTrue(Room.RoomStatus status);
    List<Room> findByHotelIdAndStatusAndIsActiveTrue(Long hotelId, Room.RoomStatus status);
}