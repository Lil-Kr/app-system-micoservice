package org.cy.micoservice.app.user.provider.service.impl;

import co.elastic.clients.elasticsearch._types.Conflicts;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateByQueryResponse;
import com.alibaba.cloud.commons.lang.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.cy.micoservice.app.common.enums.biz.DeleteStatusEnum;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.user.facade.dto.req.UserFollowerReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserFollowerRespDTO;
import org.cy.micoservice.app.entity.user.model.UserShard;
import org.cy.micoservice.app.entity.user.model.es.UserFollowerEs;
import org.cy.micoservice.app.framework.elasticsearch.starter.utils.ElasticsearchUtil;
import org.cy.micoservice.app.framework.id.starter.service.IdService;
import org.cy.micoservice.app.user.provider.config.ApplicationProperties;
import org.cy.micoservice.app.user.provider.service.UserFollowerService;
import org.cy.micoservice.app.user.provider.service.UserShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description: base on ES
 */
@Service
public class UserFollowerServiceImpl implements UserFollowerService {

  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private ElasticsearchUtil elasticsearchUtil;
  @Autowired
  private UserShardService userShardService;
  @Autowired
  private IdService idService;

  /**
   * current user is or not follower
   * @param userFollowerReqDTO
   * @return
   */
  @Override
  public boolean isFollower(UserFollowerReqDTO userFollowerReqDTO) {

    List<Query> queryList = new ArrayList<>();
    Query userIdTermQuery = Query.of(q -> q
      .term(t -> t
        .field("userId")
        .value(userFollowerReqDTO.getUserId())
      )
    );
    Query followerIdTermQuery = Query.of(q -> q
      .term(t -> t
        .field("followerId")
        .value(userFollowerReqDTO.getFollowerId())
      )
    );
    Query deletedTermQuery = Query.of(q -> q
      .term(t -> t
        .field("deleted")
        .value(DeleteStatusEnum.ACTIVE.getCode())
      )
    );
    queryList.add(userIdTermQuery);
    queryList.add(followerIdTermQuery);
    queryList.add(deletedTermQuery);
    List<UserFollowerEs> userFollowerList = elasticsearchUtil.boolQuery(applicationProperties.getUserFollowerRelationEsIndex(),
      queryList, Collections.emptyList(), Collections.emptyList(), UserFollowerEs.class);

    if (CollectionUtils.isEmpty(userFollowerList)) {
      return false;
    }
    return true;
  }

  /**
   * add user follower
   * @param userFollowerReqDTO
   * @return
   */
  @Override
  public boolean addFollower(UserFollowerReqDTO userFollowerReqDTO) {
    UserFollowerEs userFollowerEs = BeanCopyUtils.convert(userFollowerReqDTO, UserFollowerEs.class);
    Long now = DateUtil.getCurrentDateTimeMilli();
    userFollowerEs.setCreateTime(now);
    userFollowerEs.setUpdateTime(now);
    userFollowerEs.setDeleted(DeleteStatusEnum.ACTIVE.getCode());

    String id = String.valueOf(idService.getId());
    IndexResponse indexResponse = elasticsearchUtil.indexDocument(applicationProperties.getUserFollowerRelationEsIndex(),
      id, userFollowerEs);
    return Result.Created.equals(indexResponse.result()) && id.equals(indexResponse.id());
  }

  /**
   * user cancel follower
   * @param userFollowerReqDTO
   * @return
   */
  @Override
  public boolean cancel(UserFollowerReqDTO userFollowerReqDTO) {
    Query query = Query.of(q -> q
      .bool(b -> b
        .must(m1 -> m1
            .term(t -> t.field("userId")
            .value(FieldValue.of(userFollowerReqDTO.getUserId()))
          )
        )
        .must(m1 -> m1
          .term(t -> t.field("followerId")
            .value(FieldValue.of(userFollowerReqDTO.getFollowerId()))
          )
        )
      )
    );

    int delCode = DeleteStatusEnum.DELETED.getCode();
    Map<String, Object> deleteFieldMap = new HashMap<>();
    deleteFieldMap.put("deleted", delCode);
    UpdateByQueryResponse updateByQueryResponse = elasticsearchUtil.updateByQuery(applicationProperties.getUserFollowerRelationEsIndex(),
      query, deleteFieldMap, Conflicts.Proceed);
    return updateByQueryResponse.updated().equals(delCode);
  }

  /**
   * query follower
   * @param userId
   * @return
   */
  @Override
  public List<UserFollowerRespDTO> queryFollowerByUserId(Long userId) {
    Query userIdTermQuery = Query.of(q -> q
      .term(t -> t
        .field("userId")
        .value(userId)
      )
    );
    Query deletedTermQuery = Query.of(q -> q
      .term(t -> t
        .field("deleted")
        .value(DeleteStatusEnum.ACTIVE.getCode())
      )
    );
    List<Query> queryList = Arrays.asList(userIdTermQuery, deletedTermQuery);
    List<UserFollowerEs> userFollowerEsList = elasticsearchUtil.boolQuery(applicationProperties.getUserFollowerRelationEsIndex(),
      queryList, Collections.emptyList(), Collections.emptyList(), UserFollowerEs.class);
    if (CollectionUtils.isEmpty(userFollowerEsList)) {
      return Collections.emptyList();
    }

    List<Long> followerIdList = userFollowerEsList.stream().map(UserFollowerEs::getFollowerId).distinct().toList();
    if (CollectionUtils.isEmpty(followerIdList)) {
      return Collections.emptyList();
    }

    // todo: million follower need to pagination query
    List<UserShard> userShardList = userShardService.queryInUserIds(followerIdList);
    Map<Long, UserShard> userShardMap = userShardList.stream().collect(Collectors.toMap(UserShard::getUserId, item -> item));

    return userFollowerEsList.stream()
      .map(item -> {
        UserShard userShard = userShardMap.get(item.getUserId());
        UserFollowerRespDTO userRespDTO = new UserFollowerRespDTO();
        userRespDTO.setUserId(userShard.getUserId());
        if (StringUtils.isNotBlank(userShard.getAvatar())) {
          userRespDTO.setAvatarUrl(userShard.getAvatar());
        }
        userRespDTO.setSex(userShard.getSex());
        userRespDTO.setSign(userShard.getSign());
        userRespDTO.setNickName(userShard.getNickname());
        userRespDTO.setBirthDate(userShard.getBirthday());
        return userRespDTO;
      })
      .toList();
  }
}
