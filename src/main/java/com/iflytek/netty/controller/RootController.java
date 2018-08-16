package com.iflytek.netty.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iflytek.netty.dao.domain.entity.Comment;
import com.iflytek.netty.dao.mapper.CommentMapper;
import static com.iflytek.netty.utils.CommonUtil.distinctByKey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
/**
 * @author: JiangPing Li
 * @date: 2018-08-16 11:50
 */
@RestController
public class RootController {
    @Resource
    private CommentMapper commentMapper;

    @RequestMapping(path = {"/test/one"},method = RequestMethod.GET)
    public Mono<Comment> findComment(){
        return Mono.create(cityMonoSink -> cityMonoSink.success(commentMapper.selectByPrimaryKey(13)));
    }
    @RequestMapping(path = {"/test/all"},method = RequestMethod.GET)
    public Object findAllComment(){

        List<Comment> comments = commentMapper.selectAll();

        List<Comment> unique = comments
                .stream()
                .filter(distinctByKey(Comment::getId))
                .sorted(comparing(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());



        PageHelper.startPage(1, 10);
        List<Comment> list = commentMapper.selectAll();
        PageInfo page = new PageInfo<>(list);
        return page;

    }
}
