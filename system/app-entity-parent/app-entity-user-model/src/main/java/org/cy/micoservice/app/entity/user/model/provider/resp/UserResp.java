package org.cy.micoservice.app.entity.user.model.provider.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.user.model.provider.pojo.User;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResp extends User {

	@Serial
	private static final long serialVersionUID = 7090795640804631932L;

}
