package lk.icbt.oceanview.pricing;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculateTotal(BigDecimal nightlyRate, long nights);
}
