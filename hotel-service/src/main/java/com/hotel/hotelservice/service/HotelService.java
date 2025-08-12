package com.hotel.hotelservice.service;

import com.hotel.hotelservice.entity.Hotel;
import com.hotel.hotelservice.entity.Room;
import com.hotel.hotelservice.entity.RoomType;
import com.hotel.hotelservice.repository.HotelRepository;
import com.hotel.hotelservice.repository.RoomRepository;
import com.hotel.hotelservice.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    // Hotel methods
    public List<Hotel> getAllHotels() {
        return hotelRepository.findByIsActiveTrue();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.findByCityAndIsActiveTrue(city);
    }

    public List<Hotel> searchHotelsByName(String name) {
        return hotelRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setName(hotelDetails.getName());
        hotel.setDescription(hotelDetails.getDescription());
        hotel.setAddress(hotelDetails.getAddress());
        hotel.setCity(hotelDetails.getCity());
        hotel.setState(hotelDetails.getState());
        hotel.setCountry(hotelDetails.getCountry());
        hotel.setPostalCode(hotelDetails.getPostalCode());
        hotel.setPhone(hotelDetails.getPhone());
        hotel.setEmail(hotelDetails.getEmail());
        hotel.setRating(hotelDetails.getRating());
        hotel.setAmenities(hotelDetails.getAmenities());
        hotel.setImages(hotelDetails.getImages());

        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setIsActive(false);
        hotelRepository.save(hotel);
    }

    // RoomType methods
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findByIsActiveTrue();
    }

    public List<RoomType> getRoomTypesByHotel(Long hotelId) {
        return roomTypeRepository.findByHotelIdAndIsActiveTrue(hotelId);
    }

    public Optional<RoomType> getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id);
    }

    public RoomType createRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    public RoomType updateRoomType(Long id, RoomType roomTypeDetails) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        roomType.setName(roomTypeDetails.getName());
        roomType.setDescription(roomTypeDetails.getDescription());
        roomType.setBasePrice(roomTypeDetails.getBasePrice());
        roomType.setMaxOccupancy(roomTypeDetails.getMaxOccupancy());
        roomType.setSizeSqft(roomTypeDetails.getSizeSqft());
        roomType.setAmenities(roomTypeDetails.getAmenities());
        roomType.setImages(roomTypeDetails.getImages());

        return roomTypeRepository.save(roomType);
    }

    public void deleteRoomType(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found"));
        roomType.setIsActive(false);
        roomTypeRepository.save(roomType);
    }

    // Room methods
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelIdAndIsActiveTrue(hotelId);
    }

    public List<Room> getAvailableRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelIdAndStatusAndIsActiveTrue(hotelId, Room.RoomStatus.AVAILABLE);
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setFloor(roomDetails.getFloor());
        room.setStatus(roomDetails.getStatus());

        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setIsActive(false);
        roomRepository.save(room);
    }
}