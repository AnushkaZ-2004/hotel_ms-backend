package com.hotel.hotelservice.repository;

import com.hotel.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByIsActiveTrue();
    List<Hotel> findByCityAndIsActiveTrue(String city);
    List<Hotel> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
}