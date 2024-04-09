package com.andre.ReservaDeHotel.repository;

import com.andre.ReservaDeHotel.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {
}
