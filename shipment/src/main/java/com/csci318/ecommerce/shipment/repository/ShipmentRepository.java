package com.csci318.ecommerce.shipment.repository;

import com.csci318.ecommerce.shipment.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findAllByCustomerId(@Param("customerId") Long customerId);
    @Query("SELECT s.orderId FROM Shipment s WHERE s.id = :shipmentId")
    Long findOrderIdByShipmentId(Long shipmentId);
}

