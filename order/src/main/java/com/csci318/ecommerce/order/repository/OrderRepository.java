package com.csci318.ecommerce.order.repository;

import com.csci318.ecommerce.order.model.Enum.Constants;
import com.csci318.ecommerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId AND o.orderStatus = :status ORDER BY o.creationDate DESC")
    Optional<Order> findFirstOrderByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") Constants.OrderStatus status);
    List<Order> findAllOrdersByCustomerId(@Param("customerId") Long customerId);
}
