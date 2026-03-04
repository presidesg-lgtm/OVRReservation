package lk.icbt.oceanview.service;

import lk.icbt.oceanview.dao.RoomRateDAO;
import lk.icbt.oceanview.exception.ValidationException;
import lk.icbt.oceanview.model.RoomType;
import lk.icbt.oceanview.pricing.PricingStrategy;
import lk.icbt.oceanview.pricing.PricingStrategyFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class PricingService {

    private final RoomRateDAO roomRateDAO;
    private final PricingStrategyFactory strategyFactory;

    public PricingService(RoomRateDAO roomRateDAO, PricingStrategyFactory strategyFactory) {
        this.roomRateDAO = roomRateDAO;
        this.strategyFactory = strategyFactory;
    }

    public Map<String, Object> calculatePrice(String roomType, String checkIn, String checkOut) throws Exception {

        if (roomType == null || roomType.trim().isEmpty()) throw new ValidationException("roomType is required");
        if (checkIn == null || checkIn.trim().isEmpty()) throw new ValidationException("checkIn is required");
        if (checkOut == null || checkOut.trim().isEmpty()) throw new ValidationException("checkOut is required");

        // Enum validation (OOP) - throws ValidationException if invalid
        RoomType rtEnum;
        try {
            rtEnum = RoomType.from(roomType);
        } catch (IllegalArgumentException ex) {
            // If your RoomType.from currently throws IllegalArgumentException, convert it:
            throw new ValidationException(ex.getMessage());
        }

        LocalDate in = LocalDate.parse(checkIn.trim());
        LocalDate out = LocalDate.parse(checkOut.trim());
        long nights = ChronoUnit.DAYS.between(in, out);

        if (nights <= 0) throw new ValidationException("checkOut must be after checkIn");

        String rt = rtEnum.name();

        BigDecimal nightlyRate = roomRateDAO.getRateByRoomType(rt);
        if (nightlyRate == null) throw new ValidationException("Room type not found in DB: " + rt);

        PricingStrategy strategy = strategyFactory.getStrategy(rt);
        BigDecimal total = strategy.calculateTotal(nightlyRate, nights);

        Map<String, Object> result = new HashMap<>();
        result.put("roomType", rt);
        result.put("nights", nights);
        result.put("nightlyRate", nightlyRate);
        result.put("total", total);
        return result;
    }
}
