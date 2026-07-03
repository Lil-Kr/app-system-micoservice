package org.cy.micoservice.app.entity.note.content.model.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.cy.micoservice.app.entity.base.model.entity.BaseEsEntity;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NoteEs extends BaseEsEntity {

  @Serial
  private static final long serialVersionUID = 1712158323346259754L;

  /**
   *
   */
  private Long noteId;

  /**
   * 笔记作者id
   */
  private Long userId;

  /**
   * 笔记标题
   */
  private String title;

  /**
   * 笔记内容
   */
  private String content;

  /**
   * 笔记图片集合
   */
  private String imgList;

  /**
   * 笔记状态
   * org.cy.micoservice.app.note.content.facade.enums.NoteStatusEnum
   */
  private int status;

  /**
   * 评论数
   */
  private Integer reviewCount;

}