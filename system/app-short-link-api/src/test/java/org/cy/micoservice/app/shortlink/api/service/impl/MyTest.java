package org.cy.micoservice.app.shortlink.api.service.impl;

import org.junit.jupiter.api.Test;

/**
 * @Author: Lil-K
 * @Date: 2026/3/13
 * @Description:
 */
public class MyTest {

  private static long id = 647643272529969152L;

  @Test
  public void test1() {
    String shortcode = "lkwAIfB71k";
    System.out.println(shortcode.length());

    int maxValue = 10;
    System.out.println(Math.abs(id) % maxValue);
  }
}
