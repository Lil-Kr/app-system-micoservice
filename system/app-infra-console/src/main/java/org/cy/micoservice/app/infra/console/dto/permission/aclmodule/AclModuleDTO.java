package org.cy.micoservice.app.infra.console.dto.permission.aclmodule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cy.micoservice.app.infra.console.dto.permission.acl.AclDTO;
import org.cy.micoservice.app.entity.infra.console.model.entity.sys.SysAclModule;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  权限模块Dto
 * </p>
 *
 * @Author: Lil-K
 * @since 2020-11-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AclModuleDTO extends SysAclModule {

  @Serial
  private static final long serialVersionUID = -7578690008382294427L;

  private List<AclModuleDTO> aclModuleDTOList = Lists.newArrayList();

  /**
   * 权限点数据
   */
  private List<AclDTO> aclDTOList = new ArrayList<>();

  /**
   * 将权限模块数据转换为一颗树形结构
   * @return
   */
  public static AclModuleDTO adapt(SysAclModule aclModule){
    AclModuleDTO dto = new AclModuleDTO();
    BeanUtils.copyProperties(aclModule, dto);
    return dto;
  }
}
