package org.cy.micoservice.app.infra.console.vo.resp.blog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.infra.console.model.blog.BlogLabel;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2024/3/31
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogLabelResp extends BlogLabel implements Serializable {

	@Serial
	private static final long serialVersionUID = -9156896597479523967L;
}
