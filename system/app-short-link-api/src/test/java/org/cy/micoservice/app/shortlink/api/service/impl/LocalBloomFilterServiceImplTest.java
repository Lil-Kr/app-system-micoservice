package org.cy.micoservice.app.shortlink.api.service.impl;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class LocalBloomFilterServiceImplTest {

  @Test
  public void test1() {
    int timeSliceHours = 6;
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime sliceTime = now
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
      .withHour((now.getHour() / timeSliceHours) * timeSliceHours);
    String yyyyMMddHh = sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
    System.out.println(yyyyMMddHh);
    // 20260303_18
  }

}