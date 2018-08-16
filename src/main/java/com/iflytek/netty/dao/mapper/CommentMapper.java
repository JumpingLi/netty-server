package com.iflytek.netty.dao.mapper;

import com.iflytek.netty.dao.BaseMapper;
import com.iflytek.netty.dao.domain.entity.Comment;

/**
 * @author jpli3
 */
public interface CommentMapper extends BaseMapper<Comment> {
    @Override
    Comment selectByPrimaryKey(Object key);
}