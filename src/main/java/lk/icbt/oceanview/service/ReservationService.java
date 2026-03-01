package lk.icbt.oceanview.service;

import lk.icbt.oceanview.dao.ReservationDAO;
import lk.icbt.oceanview.dto.CreateReservationRequest;
import lk.icbt.oceanview.model.Reservation;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ReservationService {

    // Collection #3: Set (valid room types)
    private static final Set<String> VALID_ROOM_TYPES = new HashSet<>();
    static {
        VALID_ROOM_TYPES.add("STANDARD");
        VALID_ROOM_TYPES.add("DELUXE");
        VALID_ROOM_TYPES.add("SUITE");
    }

    private final ReservationDAO dao;

    // Dependency injection (manual) => SOLID (DIP)
    public ReservationService(ReservationDAO dao) {
        this.dao = dao;
    }

    public void createReservation(CreateReservationRequest req) throws Exception {
        // Validation (SRP: service handles business rules)
        if (req == null) throw new IllegalArgumentException("Request body is missing");
        if (isBlank(req.reservationNo)) throw new IllegalArgumentException("reservationNo is required");
        if (isBlank(req.guestName)) throw new IllegalArgumentException("guestName is required");
        if (isBlank(req.roomType)) throw new IllegalArgumentException("roomType is required");
        if (isBlank(req.checkIn) || isBlank(req.checkOut)) throw new IllegalArgumentException("checkIn and checkOut are required");

        String roomTypeUpper = req.roomType.trim().toUpperCase();
        if (!VALID_ROOM_TYPES.contains(roomTypeUpper)) {
            throw new IllegalArgumentException("Invalid roomType. Use STANDARD, DELUXE, or SUITE");
        }

        LocalDate checkIn = LocalDate.parse(req.checkIn.trim());
        LocalDate checkOut = LocalDate.parse(req.checkOut.trim());
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("checkOut must be after checkIn");
        }

        Reservation r = new Reservation(
                req.reservationNo.trim(),
                req.guestName.trim(),
                req.address == null ? null : req.address.trim(),
                req.contactNo == null ? null : req.contactNo.trim(),
                roomTypeUpper,
                checkIn,
                checkOut
        );

        dao.insert(r);
    }
    public java.util.List<java.util.Map<String, Object>> getAllReservations() throws Exception {
        return dao.findAll();
    }

    public java.util.Map<String, Object> getReservationById(String reservationNo) throws Exception {
        if (reservationNo == null || reservationNo.trim().isEmpty()) {
            throw new IllegalArgumentException("id is required");
        }
        return dao.findById(reservationNo.trim());
    }

    public boolean deleteReservation(String reservationNo) throws Exception {
        if (reservationNo == null || reservationNo.trim().isEmpty()) {
            throw new IllegalArgumentException("id is required");
        }
        int rows = dao.deleteById(reservationNo.trim());
        return rows > 0;
    }

    public boolean updateReservation(String reservationNo, lk.icbt.oceanview.dto.UpdateReservationRequest req) throws Exception {
        if (reservationNo == null || reservationNo.trim().isEmpty()) {
            throw new IllegalArgumentException("id is required");
        }
        if (req == null) {
            throw new IllegalArgumentException("Request body is missing");
        }

        // required fields for update (simpler for now)
        if (isBlank(req.guestName)) throw new IllegalArgumentException("guestName is required");
        if (isBlank(req.roomType)) throw new IllegalArgumentException("roomType is required");
        if (isBlank(req.checkIn) || isBlank(req.checkOut)) throw new IllegalArgumentException("checkIn and checkOut are required");

        String roomTypeUpper = req.roomType.trim().toUpperCase();
        if (!VALID_ROOM_TYPES.contains(roomTypeUpper)) {
            throw new IllegalArgumentException("Invalid roomType. Use STANDARD, DELUXE, or SUITE");
        }

        java.time.LocalDate checkIn = java.time.LocalDate.parse(req.checkIn.trim());
        java.time.LocalDate checkOut = java.time.LocalDate.parse(req.checkOut.trim());
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("checkOut must be after checkIn");
        }

        int rows = dao.updateById(
                reservationNo.trim(),
                req.guestName.trim(),
                req.address == null ? null : req.address.trim(),
                req.contactNo == null ? null : req.contactNo.trim(),
                roomTypeUpper,
                checkIn,
                checkOut
        );
        return rows > 0;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

}
