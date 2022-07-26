package com.pluto.tnvote.service;

import com.pluto.tnvote.pojo.TbQuestion;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
public interface TbQuestionService {
    Integer create(TbQuestion pi);

    Integer deleteBatch(String ids);

    Integer delete(Integer id);

    Integer update(TbQuestion tbQuestion);

    List<TbQuestion> query(TbQuestion tbQuestion);

    TbQuestion detail(Integer id);

    Integer count(TbQuestion tbQuestion);

}
