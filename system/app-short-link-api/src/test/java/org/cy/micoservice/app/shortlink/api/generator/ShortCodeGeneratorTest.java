package org.cy.micoservice.app.shortlink.api.generator;

import org.cy.micoservice.app.shortlink.api.config.ShortCodeConfig;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

// @SpringBootTest(classes = ShortLinkApiApplication.class)
public class ShortCodeGeneratorTest {

  // 时间戳位数
  private static final long TIMESTAMP_BITS = 41L;
  // 机器ID位数
  private static final long MACHINE_ID_BITS = 10L;
  // 序列号位数
  private static final long SEQUENCE_BITS = 12L;
  private final AtomicLong sequence = new AtomicLong(0L);
  private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;
  private static long id = 647661878328295426L;

  // @Autowired
  private ShortCodeConfig shortCodeConfig;
  // @Autowired
  private ShortCodeGenerator generator;

  @Test
  public void test1() {
    // int length = 100;
    // Set<Long> set = new HashSet<>();
    // // for (int i = 0; i < length; i++) {
    // //   long l = generator.generateId();
    // //   set.add(l);
    // // }
    // // System.out.println(set.size());
    // System.out.println(generator.generateId());
    // Assertions.assertTrue(set.size() == length);
  }
}