package lk.icbt.oceanview.pricing;

import java.util.HashMap;
import java.util.Map;

public class PricingStrategyFactory {

    private final Map<String, PricingStrategy> strategies = new HashMap<>();

    public PricingStrategyFactory() {
        strategies.put("STANDARD", new StandardPricingStrategy());
        strategies.put("DELUXE", new DeluxePricingStrategy());
        strategies.put("SUITE", new SuitePricingStrategy());
    }

    public PricingStrategy getStrategy(String roomType) {
        PricingStrategy s = strategies.get(roomType);
        if (s == null) {
            throw new IllegalArgumentException("Invalid roomType. Use STANDARD, DELUXE, or SUITE");
        }
        return s;
    }
}
