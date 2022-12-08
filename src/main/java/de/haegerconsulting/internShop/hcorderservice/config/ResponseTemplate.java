package de.haegerconsulting.internShop.hcorderservice.config;

import de.haegerconsulting.internShop.hcorderservice.contrats.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplate {

    private Inventory inventory;
}
