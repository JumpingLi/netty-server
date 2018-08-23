package com.iflytek.netty.impl;

import com.iflytek.netty.rpc.entity.TransportRequest;
import com.iflytek.netty.rpc.entity.TransportResponse;
import com.iflytek.netty.rpc.service.DemoService;
import com.iflytek.netty.rpc.util.GzipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author: JiangPing Li
 * @date: 2018-08-06 14:39
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

//    private static Map<String,String> fileIdCacheMap = Maps.newConcurrentMap();
    @Override
    public int sum(int numberA, int numberB) {
        return numberA + numberB;
    }

    @Override
    public String print() {
        return "这是来自服务器中DemoService接口的print方法打印的消息";
    }

    @Override
    public TransportResponse fileTransport(TransportRequest req) throws Exception {
        log.debug("---Server Received: " + req.getId() + ", " + req.getName() + ", " + req.getMessage());
        // 进行文件资源的还原
        byte[] attachment = GzipUtils.ungzip(req.getAttachment());
        // 获取保存目录
        String path = System.getProperty("user.dir") + File.separatorChar + "receive"
                + File.separatorChar + req.getId() + "-" + req.getName();
//        log.debug("---文件保存路径：" + path);
        FileOutputStream fos;
        if (req.getAppend()){
            // 进行文件的追加保存
            fos = new FileOutputStream(path,true);
        }else{
            // 进行文件的一次性保存
            fos = new FileOutputStream(path);
        }
        fos.write(attachment);
        fos.close();
        TransportResponse resp = new TransportResponse();
        resp.setId("文件id: " + req.getId());
        resp.setName("文件名: " + req.getName());
        resp.setMessage(req.getMessage());
        return resp;
    }
}
