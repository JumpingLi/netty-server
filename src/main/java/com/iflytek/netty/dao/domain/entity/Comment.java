package com.iflytek.netty.dao.domain.entity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author jpli3
 */
@Table(name = "`comment`")
public class Comment {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`course_id`")
    private String courseId;

    @Column(name = "`member_id`")
    private String memberId;

    @Column(name = "`start_level`")
    private Byte startLevel;

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`content`")
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return course_id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @param courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * @return member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return start_level
     */
    public Byte getStartLevel() {
        return startLevel;
    }

    /**
     * @param startLevel
     */
    public void setStartLevel(Byte startLevel) {
        this.startLevel = startLevel;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}