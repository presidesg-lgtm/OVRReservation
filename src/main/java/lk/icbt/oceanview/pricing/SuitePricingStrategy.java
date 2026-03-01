package lk.icbt.oceanview.pricing;

import java.math.BigDecimal;

public class SuitePricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculateTotal(BigDecimal nightlyRate, long nights) {
        BigDecimal base = nightlyRate.multiply(BigDecimal.valueOf(nights));
        return base.multiply(new BigDecimal("1.15")); // 15% extra
    }
}
