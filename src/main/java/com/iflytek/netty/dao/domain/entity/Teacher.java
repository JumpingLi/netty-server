package com.iflytek.netty.dao.domain.entity;

import javax.persistence.*;

@Table(name = "`teacher`")
public class Teacher {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`nickname`")
    private String nickname;

    @Column(name = "`mobile`")
    private String mobile;

    @Column(name = "`sex`")
    private Byte sex;

    @Column(name = "`studio_id`")
    private String studioId;

    @Column(name = "`dance_id`")
    private String danceId;

    @Column(name = "`avatar_url`")
    private String avatarUrl;

    @Column(name = "`identify`")
    private String identify;

    @Column(name = "`intro`")
    private String intro;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return sex
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * @return studio_id
     */
    public String getStudioId() {
        return studioId;
    }

    /**
     * @param studioId
     */
    public void setStudioId(String studioId) {
        this.studioId = studioId;
    }

    /**
     * @return dance_id
     */
    public String getDanceId() {
        return danceId;
    }

    /**
     * @param danceId
     */
    public void setDanceId(String danceId) {
        this.danceId = danceId;
    }

    /**
     * @return avatar_url
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return identify
     */
    public String getIdentify() {
        return identify;
    }

    /**
     * @param identify
     */
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    /**
     * @return intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }
}