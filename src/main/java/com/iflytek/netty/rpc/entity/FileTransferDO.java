package com.iflytek.netty.rpc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: JiangPing Li
 * @date: 2018-08-06 15:51
 */
@Data
public class FileTransferDO implements Serializable {
    private static final long serialVersionUID = 3503787946964996174L;

    private String path;
    private String fileName;
    private Integer starPos;
    private Integer endPos;
    private byte[] bytes;
}
