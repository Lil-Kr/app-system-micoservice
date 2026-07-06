package org.cy.micoservice.app.gateway.facade.dto.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2025/11/30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteChangeBodyDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -8040738653677134129L;

  private RouteConfig before;

  private RouteConfig after;

}
