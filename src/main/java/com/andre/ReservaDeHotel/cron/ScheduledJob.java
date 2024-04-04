package com.andre.ReservaDeHotel.cron;

import com.andre.ReservaDeHotel.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJob {

  @Autowired
  private CheckinService checkinService;

  @Scheduled(cron = "0 0 0 * * *")
  public void execute() {
    checkinService.deletarReservasExpiradas();
  }

}
