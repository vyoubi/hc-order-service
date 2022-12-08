package de.haegerconsulting.internShop.hcorderservice.contrats;

import de.haegerconsulting.internShop.hcorderservice.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Order order;
    private BigDecimal amount;
    private String internalId;
    private String status;
}

