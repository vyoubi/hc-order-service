package de.haegerconsulting.internShop.hcorderservice.contrats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    private Long smsId;
    private String phoneNumber;
    private String message;
}
