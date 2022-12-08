package de.haegerconsulting.internShop.hcorderservice.repositories;

import de.haegerconsulting.internShop.hcorderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getByOrderNumber(String orderNummer);
}
