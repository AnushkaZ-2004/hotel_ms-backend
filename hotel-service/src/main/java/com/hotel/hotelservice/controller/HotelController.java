package com.hotel.hotelservice.controller;

import com.hotel.hotelservice.entity.Hotel;
import com.hotel.hotelservice.entity.Room;
import com.hotel.hotelservice.entity.RoomType;
import com.hotel.hotelservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Hotel endpoints
    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hotels/search")
    public List<Hotel> searchHotels(@RequestParam(required = false) String city,
                                    @RequestParam(required = false) String name) {
        if (city != null) {
            return hotelService.getHotelsByCity(city);
        } else if (name != null) {
            return hotelService.searchHotelsByName(name);
        } else {
            return hotelService.getAllHotels();
        }
    }

    @PostMapping("/hotels")
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        try {
            Hotel updatedHotel = hotelService.updateHotel(id, hotel);
            return ResponseEntity.ok(updatedHotel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Room Type endpoints
    @GetMapping("/room-types")
    public List<RoomType> getAllRoomTypes() {
        return hotelService.getAllRoomTypes();
    }

    @GetMapping("/hotels/{hotelId}/room-types")
    public List<RoomType> getRoomTypesByHotel(@PathVariable Long hotelId) {
        return hotelService.getRoomTypesByHotel(hotelId);
    }

    @GetMapping("/room-types/{id}")
    public ResponseEntity<RoomType> getRoomTypeById(@PathVariable Long id) {
        return hotelService.getRoomTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/room-types")
    public RoomType createRoomType(@RequestBody RoomType roomType) {
        return hotelService.createRoomType(roomType);
    }

    @PutMapping("/room-types/{id}")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable Long id, @RequestBody RoomType roomType) {
        try {
            RoomType updatedRoomType = hotelService.updateRoomType(id, roomType);
            return ResponseEntity.ok(updatedRoomType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/room-types/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        try {
            hotelService.deleteRoomType(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Room endpoints
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return hotelService.getAllRooms();
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public List<Room> getRoomsByHotel(@PathVariable Long hotelId) {
        return hotelService.getRoomsByHotel(hotelId);
    }

    @GetMapping("/hotels/{hotelId}/rooms/available")
    public List<Room> getAvailableRoomsByHotel(@PathVariable Long hotelId) {
        return hotelService.getAvailableRoomsByHotel(hotelId);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return hotelService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody Room room) {
        return hotelService.createRoom(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            Room updatedRoom = hotelService.updateRoom(id, room);
            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            hotelService.deleteRoom(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}