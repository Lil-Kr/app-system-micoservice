package org.cy.micoservice.app.shortlink.api.controller;

import com.alibaba.fastjson2.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.framework.web.starter.annotations.NoAuthCheck;
import org.cy.micoservice.app.shortlink.api.generator.ShortCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: Lil-K
 * @Date: 2026/3/19
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private ShortCodeGenerator shortCodeGenerator;

  @NoAuthCheck
  @GetMapping("/getId")
  public void getId() {
    Set<Long> set = new TreeSet<>();
    for (int i = 0; i < 100; i ++) {
      set.add(shortCodeGenerator.generateId());
    }
    System.out.println(JSONArray.toJSONString(set));
    System.out.println(set.size());
  }
}