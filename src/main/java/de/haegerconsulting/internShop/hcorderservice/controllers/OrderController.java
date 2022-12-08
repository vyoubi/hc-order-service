package de.haegerconsulting.internShop.hcorderservice.controllers;

import de.haegerconsulting.internShop.hcorderservice.contrats.PaymentResponse;
import de.haegerconsulting.internShop.hcorderservice.dto.OrderDto;
import de.haegerconsulting.internShop.hcorderservice.entities.Order;
import de.haegerconsulting.internShop.hcorderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final StreamBridge streamBridge;

    @PostMapping
    public String placeOrder(@RequestBody OrderDto orderDto) {
        log.info("Order Place Order in OrderController");

        Order order = new Order();
        order.setSkuCode(orderDto.getSkuCode());
        order.setPrice(orderDto.getPrice());
        order.setQuantity(orderDto.getQuantity());
        order.setOrderNumber(UUID.randomUUID().toString());

        orderService.saveOrder(order);
        log.info("Sending Order Details to Notification Service");
        streamBridge.send("notificationEventSupplier-out-0", order.getOrderNumber());
        return "Order Place Succesfully";
    }

    @GetMapping("/{orderNumber}")
    public PaymentResponse placeOrder(@PathVariable("orderNumber") String orderNumber){
        return orderService.placeOrder(orderNumber);
    }

    @GetMapping
    public List<Order> findAll() {
        log.info("find all orders in OrderController");
        return orderService.findAll();
    }

    @DeleteMapping("/remove/{id}")
    public void deleteOrder(@PathVariable("id") Long orderId) {
        log.info("Delete Order in ProductController");
        orderService.deleteOrder(orderId);
    }
}
