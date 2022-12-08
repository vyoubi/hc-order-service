package de.haegerconsulting.internShop.hcorderservice.services;

import de.haegerconsulting.internShop.hcorderservice.config.ResponseTemplate;
import de.haegerconsulting.internShop.hcorderservice.contrats.PaymentResponse;
import de.haegerconsulting.internShop.hcorderservice.entities.Order;

import java.util.List;

public interface OrderService {

    ResponseTemplate saveOrder(Order order);
    PaymentResponse placeOrder(String orderNumber);

    List<Order> findAll();
    void deleteOrder(Long orderId);
}
