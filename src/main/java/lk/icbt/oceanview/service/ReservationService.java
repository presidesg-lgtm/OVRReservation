package lk.icbt.oceanview.service;

import lk.icbt.oceanview.dao.ReservationDAO;
import lk.icbt.oceanview.dto.CreateReservationRequest;
import lk.icbt.oceanview.dto.UpdateReservationRequest;
import lk.icbt.oceanview.exception.NotFoundException;
import lk.icbt.oceanview.exception.ValidationException;
import lk.icbt.oceanview.model.Reservation;
import lk.icbt.oceanview.model.RoomType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReservationService {

    private final ReservationDAO dao;

    // Dependency injection (manual) => SOLID (DIP)
    public ReservationService(ReservationDAO dao) {
        this.dao = dao;
    }

    // -----------------------
    // CREATE
    // -----------------------
    public void createReservation(CreateReservationRequest req) throws Exception {
        requireBody(req);

        String reservationNo = requireTrimmed(req.reservationNo, "reservationNo is required");
        String guestName = requireTrimmed(req.guestName, "guestName is required");
        RoomType roomType = RoomType.from(req.roomType); // throws ValidationException if invalid

        LocalDate checkIn = parseDate(req.checkIn, "checkIn");
        LocalDate checkOut = parseDate(req.checkOut, "checkOut");
        validateDateRange(checkIn, checkOut);

        Reservation r = new Reservation(
                reservationNo,
                guestName,
                trimOrNull(req.address),
                trimOrNull(req.contactNo),
                roomType.name(),
                checkIn,
                checkOut
        );

        dao.insert(r);
    }

    // -----------------------
    // READ
    // -----------------------
    public List<Map<String, Object>> getAllReservations() throws Exception {
        return dao.findAll();
    }

    public Map<String, Object> getReservationById(String reservationNo) throws Exception {
        String id = requireTrimmed(reservationNo, "id is required");

        Map<String, Object> one = dao.findById(id);
        if (one == null) {
            throw new NotFoundException("Reservation not found: " + id);
        }
        return one;
    }

    // -----------------------
    // DELETE
    // -----------------------
    public void deleteReservation(String reservationNo) throws Exception {
        String id = requireTrimmed(reservationNo, "id is required");

        int rows = dao.deleteById(id);
        if (rows == 0) {
            throw new NotFoundException("Reservation not found: " + id);
        }
    }

    // -----------------------
    // UPDATE
    // -----------------------
    public void updateReservation(String reservationNo, UpdateReservationRequest req) throws Exception {
        String id = requireTrimmed(reservationNo, "id is required");
        requireBody(req);

        String guestName = requireTrimmed(req.guestName, "guestName is required");
        RoomType roomType = RoomType.from(req.roomType);

        LocalDate checkIn = parseDate(req.checkIn, "checkIn");
        LocalDate checkOut = parseDate(req.checkOut, "checkOut");
        validateDateRange(checkIn, checkOut);

        int rows = dao.updateById(
                id,
                guestName,
                trimOrNull(req.address),
                trimOrNull(req.contactNo),
                roomType.name(),
                checkIn,
                checkOut
        );

        if (rows == 0) {
            throw new NotFoundException("Reservation not found: " + id);
        }
    }

    // -----------------------
    // Helpers
    // -----------------------
    private void requireBody(Object body) {
        if (body == null) {
            throw new ValidationException("Request body is missing");
        }
    }

    private String requireTrimmed(String value, String messageIfMissing) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(messageIfMissing);
        }
        return value.trim();
    }

    private String trimOrNull(String s) {
        return (s == null) ? null : s.trim();
    }

    private LocalDate parseDate(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required");
        }
        try {
            return LocalDate.parse(value.trim());
        } catch (Exception e) {
            throw new ValidationException(fieldName + " must be a valid date (YYYY-MM-DD)");
        }
    }

    private void validateDateRange(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new ValidationException("checkOut must be after checkIn");
        }
    }
}