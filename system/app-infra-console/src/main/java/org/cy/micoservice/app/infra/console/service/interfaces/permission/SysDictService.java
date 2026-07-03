package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.entity.infra.console.model.entity.sys.SysDict;
import org.cy.micoservice.app.infra.console.vo.req.sys.dict.DictDetailReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.dict.DictListPageReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.dict.DictSaveReq;
import org.cy.micoservice.app.infra.console.vo.resp.sys.dic.SysDictDetailResp;
import org.cy.micoservice.app.infra.console.vo.resp.sys.dic.SysDictResp;

import java.util.List;
import java.util.Map;

/**
 * @Author: Lil-K
 * @Date: 2025/3/5
 * @Description:
 */
public interface SysDictService extends IService<SysDict> {

	ApiResp<String> add(DictSaveReq req);

	ApiResp<String> edit(DictSaveReq req);

	ApiPageResult<SysDictResp> listAll();

	ApiResp<SysDictResp> dictDetailList(DictDetailReq req);

	SysDictResp getDict(Long surrogateId);

	ApiPageResult<SysDictResp> pageList(DictListPageReq req);

	ApiResp<String> delete(Long surrogateId);

	ApiResp<Map<String, List<SysDictDetailResp>>> dictDetailMapping();
}