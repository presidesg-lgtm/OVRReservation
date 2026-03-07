package lk.icbt.oceanview.dao;

import lk.icbt.oceanview.db.DB;
import lk.icbt.oceanview.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReservationDAO {

    public void insert(Reservation r) throws Exception {
        String sql = "INSERT INTO reservations " +
                "(reservation_no, guest_name, address, contact_no, room_type, check_in, check_out) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getReservationNo());
            ps.setString(2, r.getGuestName());
            ps.setString(3, r.getAddress());
            ps.setString(4, r.getContactNo());
              ps.setString(5, r.getRoomType());
            ps.setDate(6, java.sql.Date.valueOf(r.getCheckIn()));
            ps.setDate(7, java.sql.Date.valueOf(r.getCheckOut()));

            ps.executeUpdate();
        }
    }
    public java.util.List<java.util.Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT reservation_no, guest_name, address, contact_no, room_type, check_in, check_out, created_at " +
                "FROM reservations ORDER BY created_at DESC";

        java.util.List<java.util.Map<String, Object>> list = new java.util.ArrayList<>();

        try (java.sql.Connection con = lk.icbt.oceanview.db.DB.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                java.util.Map<String, Object> row = new java.util.HashMap<>();
                row.put("reservationNo", rs.getString("reservation_no"));
                row.put("guestName", rs.getString("guest_name"));
                row.put("address", rs.getString("address"));
                row.put("contactNo", rs.getString("contact_no"));
                row.put("roomType", rs.getString("room_type"));
                row.put("checkIn", rs.getDate("check_in").toString());
                row.put("checkOut", rs.getDate("check_out").toString());
                row.put("createdAt", rs.getTimestamp("created_at").toString());
                list.add(row);
            }
        }
        return list;
    }
    public java.util.Map<String, Object> findById(String reservationNo) throws Exception {
        String sql = "SELECT reservation_no, guest_name, address, contact_no, room_type, check_in, check_out, created_at " +
                "FROM reservations WHERE reservation_no = ?";

        try (java.sql.Connection con = lk.icbt.oceanview.db.DB.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNo);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                java.util.Map<String, Object> row = new java.util.HashMap<>();
                row.put("reservationNo", rs.getString("reservation_no"));
                row.put("guestName", rs.getString("guest_name"));
                row.put("address", rs.getString("address"));
                row.put("contactNo", rs.getString("contact_no"));
                row.put("roomType", rs.getString("room_type"));
                row.put("checkIn", rs.getDate("check_in").toString());
                row.put("checkOut", rs.getDate("check_out").toString());
                row.put("createdAt", rs.getTimestamp("created_at").toString());
                return row;
            }
        }
    }
    public int deleteById(String reservationNo) throws Exception {
        String sql = "DELETE FROM reservations WHERE reservation_no = ?";

        try (java.sql.Connection con = lk.icbt.oceanview.db.DB.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNo);
            return ps.executeUpdate(); // returns number of rows deleted (0 or 1)
        }
    }
    public int updateById(String reservationNo,
                          String guestName,
                          String address,
                          String contactNo,
                          String roomType,
                          java.time.LocalDate checkIn,
                          java.time.LocalDate checkOut) throws Exception {

        String sql = "UPDATE reservations SET guest_name=?, address=?, contact_no=?, room_type=?, check_in=?, check_out=? " +
                "WHERE reservation_no=?";

        try (java.sql.Connection con = lk.icbt.oceanview.db.DB.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, guestName);
            ps.setString(2, address);
            ps.setString(3, contactNo);
            ps.setString(4, roomType);
            ps.setDate(5, java.sql.Date.valueOf(checkIn));
            ps.setDate(6, java.sql.Date.valueOf(checkOut));
            ps.setString(7, reservationNo);

            return ps.executeUpdate(); // 0 = not found, 1 = updated
        }
    }

}
