package org.cy.micoservice.app.shortlink.api.service.impl;

import com.alibaba.fastjson2.JSONArray;
import org.cy.micoservice.app.shortlink.api.utils.DigestUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_UNDERSCORE_SPLIT;

public class ShortUrlServiceImplTest {

  private static final String originUrl_1 = "https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/%E5%B7%A6%E8%80%B3%E5%90%AC%E9%A3%8E";
  private static final String originUrl_2 = "https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/%E5%B7%A6%E8%80%B3%E5%90%AC%E9%A3%8A";

  public static List<String> generateMultipleUrlHashes(String originUrl) {
    List<String> hashList = new ArrayList<>();

    // 主哈希: MD5
    hashList.add(DigestUtils.md5(originUrl));

    // 备用哈希1: SHA-256的前32位
    String sha256 = DigestUtils.sha256(originUrl);
    hashList.add(sha256.substring(0, 32));

    /**
     * 备用哈希2: 带时间戳盐值的MD5
     * 秒级时间戳
     */
    String saltedUrl = String.format(COMMENT_FORMAT_UNDERSCORE_SPLIT, originUrl, System.currentTimeMillis() / 1000);
    hashList.add(DigestUtils.md5(saltedUrl));

    // 备用哈希3: URL长度 + MD5的组合
    String lengthPrefixedUrl = String.format(COMMENT_FORMAT_UNDERSCORE_SPLIT, originUrl.length(), originUrl);
    hashList.add(DigestUtils.md5(lengthPrefixedUrl));

    return hashList;
  }

  @Test
  public void test() {
    List<String> res1 = generateMultipleUrlHashes(originUrl_1);
    System.out.println(JSONArray.toJSONString(res1));
    List<String> res2 = generateMultipleUrlHashes(originUrl_2);
    System.out.println(JSONArray.toJSONString(res2));
  }

}