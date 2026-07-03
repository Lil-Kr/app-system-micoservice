package org.cy.micoservice.app.infra.console.vo.resp.gateway;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.cy.micoservice.app.entity.gateway.model.LogPrintStrategy;

import java.io.Serial;


/**
 * @Author: Lil-K
 * @Date: 2025/12/1
 * @Description: 分页查询
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LogPrintStrategyPageResp extends LogPrintStrategy {

  @Serial
  private static final long serialVersionUID = 433150542738530423L;

}