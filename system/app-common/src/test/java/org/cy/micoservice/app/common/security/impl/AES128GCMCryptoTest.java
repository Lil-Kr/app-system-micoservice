package org.cy.micoservice.app.common.security.impl;

import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;

public class AES128GCMCryptoTest {

  @Test
  public void testAES128GCMCrypto() throws Exception {
    AES128GCMCrypto aes128GCMCrypto = new AES128GCMCrypto("PxMNarWuqoNFFGJ5QGgesg==");
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userId", 1330756438846476314l);
    jsonObject.put("phone", "18898764532");
    String jsonStr = jsonObject.toJSONString();
    String encrypt = aes128GCMCrypto.encrypt(jsonStr);
    System.out.println(encrypt);
  }

}