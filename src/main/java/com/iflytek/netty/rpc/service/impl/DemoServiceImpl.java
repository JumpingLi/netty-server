package com.iflytek.netty.rpc.service.impl;

import com.iflytek.netty.rpc.entity.TransportRequest;
import com.iflytek.netty.rpc.entity.TransportResponse;
import com.iflytek.netty.rpc.service.DemoService;
import com.iflytek.netty.rpc.util.GzipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @author: JiangPing Li
 * @date: 2018-08-06 14:39
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public int sum(int numberA, int numberB) {
        return numberA + numberB;
    }

    @Override
    public String print() {
        return "这是来自服务器中DemoService接口的print方法打印的消息";
    }

    @Override
    public TransportResponse fileTransport(TransportRequest req) {
        log.debug("---Server Received: " + req.getId() + ", " + req.getName() + ", " + req.getMessage());
        TransportResponse resp = new TransportResponse();
        resp.setCode(200);
        resp.setId("文件大小:" + req.getId());
        resp.setName("传输文件名:" + req.getName());
        resp.setMessage("响应消息: " + req.getMessage());
        String[] nameArray = req.getName().split("\\.");
//        try{
//            byte[] attachment = GzipUtils.ungzip(req.getAttachment());
//            String path = System.getProperty("user.dir") + File.separatorChar + "receive"
//                    + File.separatorChar + nameArray[0] + "_" + UUID.randomUUID() + "." + nameArray[1];
//            FileOutputStream fos;
//            if (req.getAppend()){
//                fos = new FileOutputStream(path,true);
//            }else{
//                fos = new FileOutputStream(path);
//            }
//            fos.write(attachment);
//            fos.close();
//        }catch (Exception e){
//            resp.setCode(500);
//            resp.setMessage("服务端发生异常 :" + e.getMessage());
//        }
        return resp;
    }
}
