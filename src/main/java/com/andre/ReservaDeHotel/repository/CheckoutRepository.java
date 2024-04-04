package com.andre.ReservaDeHotel.repository;

import com.andre.ReservaDeHotel.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
}
