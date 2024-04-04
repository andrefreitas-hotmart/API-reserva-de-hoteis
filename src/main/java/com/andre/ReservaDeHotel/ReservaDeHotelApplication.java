package com.andre.ReservaDeHotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservaDeHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaDeHotelApplication.class, args);
	}

}
