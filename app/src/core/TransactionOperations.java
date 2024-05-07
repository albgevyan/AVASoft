package AvaBank.core;

import java.math.BigDecimal;

public interface TransactionOperations {
    public void depositAmount(BigDecimal amount);
    public void withdrawAmount(BigDecimal amount);
}
