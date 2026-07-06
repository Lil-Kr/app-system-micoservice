package org.cy.micoservice.app.infra.console.facade.constants;

/**
 * @Author: Lil-K
 * @Date: 2026/7/4
 * @Description:
 */
public class InfraConsoleConstant {

  /**
   * update nacos properties version lock
   */
  public static final String CHANGE_ROUTE_CONFIG_KEY = "change_route_lock";
  public static final String CHANGE_ROUTE_CONFIG_VALUE = "change_route_value";

  /**
   * internal call common header
   */
  public static final String INTERNAL_CALL_HEADER = "X-INFRA-CONSOLE-INTERNAL-CALL";
  public static final String INTERNAL_CALL_HEADER_VALUE = "internal";
  public static final String INFRA_CONSOLE_SDK_CLIENT = "X-INFRA-CONSOLE-SDK-CLIENT";

  /**
   * infra-console system token header key
   */
  public static final String INFRA_TOKEN_HEADER = "authorization";
}