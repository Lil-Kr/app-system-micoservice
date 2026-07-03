package org.cy.micoservice.app.user.api.vo.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResp implements Serializable {

	@Serial
	private static final long serialVersionUID = 7090795640804631932L;

	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String nickname;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * salt value
	 */
	private String salt;

	/**
	 * avatar
	 */
	private String avatar;

	/**
	 * 个性签名
	 */
	private String sign;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * delete statue
	 */
	private Integer deleted;

	/**
	 * 上次登录时间
	 */
	private LocalDateTime lastLoginTime;

	/**
	 * 首次注册时间
	 */
	private LocalDateTime registryTime;

	/**
	 * 生日
	 */
	private LocalDate birthday;

	/**
	 * create time
	 */
	private LocalDateTime createTime;

	/**
	 * update time
	 */
	private LocalDateTime updateTime;
}
