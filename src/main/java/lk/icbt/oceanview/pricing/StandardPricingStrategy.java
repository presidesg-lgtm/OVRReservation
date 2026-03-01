package lk.icbt.oceanview.pricing;

import java.math.BigDecimal;

public class StandardPricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculateTotal(BigDecimal nightlyRate, long nights) {
        return nightlyRate.multiply(BigDecimal.valueOf(nights));
    }
}
