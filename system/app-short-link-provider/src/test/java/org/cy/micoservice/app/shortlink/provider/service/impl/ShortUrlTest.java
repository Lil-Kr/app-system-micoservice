package org.cy.micoservice.app.shortlink.provider.service.impl;

import com.alibaba.fastjson2.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/2/25
 * @Description:
 */
@Slf4j
public class ShortUrlTest {

  @Test
  public void test1() {
    List<String> list = JSONArray.parseArray("[\"b8d31362d42f7de804d6df9f91d54b1d\",\"82cbb4a8b646d80415683b398f321651\",\"b846fc49ad34a7ce05a5a673b736d22a\",\"38a5be650349ab431ac160acb355de45\"]", String.class);
    list.stream().forEach(s -> System.out.println(s.length()));
  }
}