package com.example.seedlist.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class DingCallBackController {

    private static final String AES_TOKEN = "6COLyRkPtKbSU8mKdd";
    private static final String AES_KEY = "pzNjGu1vspVduvmlrZjKcP2RrCIU8IeUVM6sMa8oFMJ";
    private static final String OWNER_KEY = "dingepbkrizch5znzlrp";
    
    @RequestMapping("/ding/callback")
    public Map<String, String> callBack(
            @RequestParam(value = "msg_signature", required = false) String msg_signature,
            @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestBody(required = false) JSONObject json) {
        try {
            // 1. 从http请求中获取加解密参数
            // 2. 使用加解密类型
            // Constant.OWNER_KEY 说明：
            // 1、开发者后台配置的订阅事件为应用级事件推送，此时OWNER_KEY为应用的APP_KEY。
            // 2、调用订阅事件接口订阅的事件为企业级事件推送，
            //      此时OWNER_KEY为：企业的appkey（企业内部应用）或SUITE_KEY（三方应用）
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(AES_TOKEN, AES_KEY, OWNER_KEY);
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, encryptMsg);

            // 3. 反序列化回调事件json数据
            JSONObject eventJson = JSON.parseObject(decryptMsg);
            String eventType = eventJson.getString("EventType");

            // 4. 根据EventType分类处理
            if ("check_url".equals(eventType)) {
                // 测试回调url的正确性
                log.info("测试回调url的正确性");
            } else if ("user_add_org".equals(eventType)) {
                // 处理通讯录用户增加事件
                log.info("发生了：" + eventType + "事件");
            } else {
                // 添加其他已注册的
                log.info("发生了：" + eventType + "事件");
                log.info("msg:{}",decryptMsg);
            }
            // 5. 返回success的加密数据
            Map<String, String> successMap = callbackCrypto.getEncryptedMap("success");
            return successMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
