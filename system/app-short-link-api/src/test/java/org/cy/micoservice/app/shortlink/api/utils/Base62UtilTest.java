package org.cy.micoservice.app.shortlink.api.utils;

import org.junit.Test;

public class Base62UtilTest {

  @Test
  public void test1() {
    long maxValue = Base62Util.getMaxValue(6);
    System.out.println(maxValue);
  }

}