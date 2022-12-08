package de.haegerconsulting.internShop.hcorderservice.contrats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    private Long inventoryId;
    private String skuCode;
    private Integer stock;
}
