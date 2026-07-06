package org.cy.micoservice.app.infra.console.dto.permission.org;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysOrg;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgReq;
import org.springframework.beans.BeanUtils;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@ToString
public class OrgDTO extends SysOrg {

  @Serial
  private static final long serialVersionUID = -4389896129961569972L;

  /**
   * 参数转换为实体类
   * @param param
   * @return
   */
  public static SysOrg paramToSysOrg(OrgReq param) {
    SysOrg org = SysOrg.builder().build();
    BeanUtils.copyProperties(param,org);
    return org;
  }

  public static SysOrg paramToSysOrg(SysOrg param) {
    SysOrg org = SysOrg.builder().build();
    BeanUtils.copyProperties(param,org);
    return org;
  }
}
