package com.situ.shoplook2025.login.api.dao;

import com.situ.shoplook2025.login.api.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SmsSender {

    // 阿里云市场短信API地址
    private static final String HOST = "https://dfsns.market.alicloudapi.com";
    private static final String PATH = "/data/send_sms";
    private static final String METHOD = "POST";

    // 你的 AppCode，从阿里云市场购买短信服务后获得
    private static final String APPCODE = "*************"; // ⚠️ 请替换成你自己的 AppCode

//    public static void main(String[] args) {
//        sendSms("手机号", "68669", "CST_ptdie100"); // ⚠️ 手机号、验证码、模板ID请替换成真实值
//    }

    /**
     * 发送短信
     *
     * @param phoneNumber 接收短信的手机号，如 "13800138000"
     * @param code        验证码，如 "1234"，会拼接到短信内容中，或由模板决定
     * @param templateId  短信模板ID，如 "CST_ptdie100"（示例），正式环境请使用你自己的审核通过模板
     */
    public static void sendSms(String phoneNumber, String code, String templateId) {
        String url = HOST + PATH;

        // 请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        // 请求体参数
        Map<String, String> bodys = new HashMap<>();
        bodys.put("phone_number", phoneNumber);
        bodys.put("template_id", templateId);
        bodys.put("content", "code:" + code); // 有些模板可能不需要 content，具体看阿里云文档或模板要求

        HttpResponse response = null;
        try {
            // 发送 POST 请求
            response = HttpUtils.doPost(HOST, PATH, METHOD, headers, null, bodys);

            // 解析返回结果
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("短信发送结果: " + result);

            // 你可以根据返回的 JSON 判断是否发送成功，比如查找 "status": "OK"
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("短信发送失败: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}