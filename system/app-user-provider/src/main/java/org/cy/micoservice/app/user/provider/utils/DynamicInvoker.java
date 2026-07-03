package org.cy.micoservice.app.user.provider.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;

/**
 * @Author: Lil-K
 * @Date: 2026/6/8
 * @Description: dynamic routing invoker, use for user info migration
 */
@Component
public class DynamicInvoker {

  private Random random = new Random();

  /**
   * 根据概率决定调用哪个函数
   * @param probabilityA 调用函数A的概率, 取值范围 [0, 1]
   * @param functionA 函数A
   * @param functionB 函数B
   */
  public void invokeByProbability(double probabilityA, Runnable functionA, Runnable functionB) {
    // 验证概率值是否在有效范围内
    if (probabilityA < 0 || probabilityA > 1) {
      throw new IllegalArgumentException("概率值必须在0到1之间");
    }

    // 生成0到1之间的随机数
    double randomValue = random.nextDouble();

    // 根据概率决定调用哪个函数
    if (randomValue < probabilityA) {
      functionA.run();
    } else {
      functionB.run();
    }
  }

  /**
   * 根据概率决定调用哪个有返回值的函数
   * @param probabilityA 调用函数A的概率, 取值范围 [0, 1]
   * @param functionA 函数A
   * @param functionB 函数B
   * @return 被调用函数的返回值
   */
  public <T> T invokeByProbabilityWithResult(double probabilityA, Supplier<T> functionA, Supplier<T> functionB) {
    // 验证概率值是否在有效范围内
    if (probabilityA < 0 || probabilityA > 1) {
      throw new IllegalArgumentException("概率值必须在0到1之间");
    }

    // 生成0到1之间的随机数
    double randomValue = random.nextDouble();

    // 根据概率决定调用哪个函数并返回结果
    return randomValue < probabilityA ? functionA.get() : functionB.get();
  }
}