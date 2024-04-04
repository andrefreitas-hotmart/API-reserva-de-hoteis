package com.andre.ReservaDeHotel.repository;

import com.andre.ReservaDeHotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
