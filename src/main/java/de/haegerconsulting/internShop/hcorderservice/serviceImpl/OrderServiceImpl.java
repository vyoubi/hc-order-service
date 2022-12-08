package de.haegerconsulting.internShop.hcorderservice.serviceImpl;

import de.haegerconsulting.internShop.hcorderservice.config.ResponseTemplate;
import de.haegerconsulting.internShop.hcorderservice.contrats.Payment;
import de.haegerconsulting.internShop.hcorderservice.contrats.PaymentResponse;
import de.haegerconsulting.internShop.hcorderservice.contrats.SmsRequest;
import de.haegerconsulting.internShop.hcorderservice.entities.Order;
import de.haegerconsulting.internShop.hcorderservice.repositories.OrderRepository;
import de.haegerconsulting.internShop.hcorderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    @Override
    public ResponseTemplate saveOrder(Order order) {
        String skuCode = order.getSkuCode();
        ResponseTemplate template = new ResponseTemplate();
        Boolean inventory = restTemplate.getForObject("http://INVENTORY-SERVICE/api/inventories/" + skuCode, Boolean.class);
        if (inventory) {
            orderRepository.save(order);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            SmsRequest objSms = new SmsRequest();
            objSms.setPhoneNumber("+4917658028483");
            objSms.setMessage("Order placed successfully - Order Number is " + order.getOrderNumber() );
            HttpEntity<SmsRequest> httpEntity = new HttpEntity<>(objSms, headers);
            restTemplate.postForObject("http://NOTIFICATION-SERVICE/api/notifications/sms/order", httpEntity, SmsRequest.class);

            return template;
        } else {
            return null;
        }
    }

    @Override
    public PaymentResponse placeOrder(String orderNumber){
        Order order = orderRepository.getByOrderNumber(orderNumber);

        if (order != null) {
            Payment paymentReq = new Payment();
            paymentReq.setOrderId(order.getOrderId());
            paymentReq.setAmount(order.getPrice());

            Payment paymentResponse =
                    restTemplate.postForObject("http://PAYMENT-SERVICE/api/payments/doPay/", paymentReq, Payment.class);

            PaymentResponse response = new PaymentResponse();
            response.setOrder(order);
            response.setStatus(paymentResponse.getPaymentStatus());
            response.setAmount(paymentResponse.getAmount());
            response.setInternalId(paymentResponse.getInternalId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            SmsRequest objSms = new SmsRequest();
            objSms.setPhoneNumber("+4917658028483");
            objSms.setMessage("Payment Status : " + response.getStatus() );
            HttpEntity<SmsRequest> httpEntity = new HttpEntity<>(objSms, headers);
            restTemplate.postForObject("http://NOTIFICATION-SERVICE/api/notifications/sms/payment", httpEntity, SmsRequest.class);

            return response;
        } else {
            return null;
        }

    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
