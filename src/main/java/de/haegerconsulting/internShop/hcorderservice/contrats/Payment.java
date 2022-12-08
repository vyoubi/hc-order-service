package de.haegerconsulting.internShop.hcorderservice.contrats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long paymentId;
    private String paymentStatus;
    private String internalId;
    private Long orderId;
    private BigDecimal amount;
}
