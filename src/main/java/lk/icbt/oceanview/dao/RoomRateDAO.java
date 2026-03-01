package lk.icbt.oceanview.dao;

import lk.icbt.oceanview.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomRateDAO {

    public List<Map<String, Object>> getAllRates() throws Exception {
        String sql = "SELECT room_type, nightly_rate FROM room_rates ORDER BY room_type";

        List<Map<String, Object>> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("roomType", rs.getString("room_type"));
                row.put("nightlyRate", rs.getBigDecimal("nightly_rate"));
                list.add(row);
            }
        }
        return list;
    }

    public java.math.BigDecimal getRateByRoomType(String roomType) throws Exception {
        String sql = "SELECT nightly_rate FROM room_rates WHERE room_type = ?";

        try (java.sql.Connection con = lk.icbt.oceanview.db.DB.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, roomType);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return rs.getBigDecimal("nightly_rate");
            }
        }
    }
}