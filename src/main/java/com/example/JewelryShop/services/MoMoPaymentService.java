package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.IpnMoMoWebhookDTO;
import com.example.JewelryShop.utils.HmacSHA256Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MoMoPaymentService {

    public ResponseEntity<?> ipnMoMoWebhook(IpnMoMoWebhookDTO ipnMoMoWebhookDTO) {
        System.out.println("IPN MoMo Webhook: " + ipnMoMoWebhookDTO.getOrderId());
        return ResponseEntity.ok().build();
    }

    public String createPayment() throws Exception {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"partnerCode\": \"MOMO\",\r\n    \"accessKey\": \"F8BBA842ECF85\",\r\n    \"requestId\": \"MOMO1716383656290\",\r\n    \"amount\": \"50000\",\r\n    \"orderId\": \"MOMO1716383656290\",\r\n    \"orderInfo\": \"pay with MoMo\",\r\n    \"redirectUrl\": \"https://momo.vn/return\",\r\n    \"ipnUrl\": \"https://callback.url/notify\",\r\n    \"extraData\": \"\",\r\n    \"requestType\": \"captureWallet\",\r\n    \"signature\": \"06e53a841ce33bcc1d51d4b702ebb7726304dad905dbc361fd0822e849d61549\",\r\n    \"lang\": \"en\"\r\n}");
//        Request request = new Request.Builder()
//                .url("https://test-payment.momo.vn/v2/gateway/api/create")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }


        String partnerCode = "MOMO";
        String accessKey = "F8BBA842ECF85";
        String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
        String requestId = partnerCode + new Date().getTime();
        String orderId = requestId;
        String orderInfo = "pay with MoMo";
        String redirectUrl = "https://momo.vn/return";
        String ipnUrl = "https://50dq8bhk-8080.asse.devtunnels.ms/api/payment/ipn_momo_webhook";
        String amount = "50000";
        String requestType = "captureWallet";
        String order_id = "{\"orderId\":" + orderId + "}";
        String extraData = Base64.getEncoder().encodeToString(order_id.getBytes());
        ;

        String rawSignature = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;


        String signature = HmacSHA256Utils.hmacSHA256(rawSignature, secretKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("partnerCode", partnerCode);
        requestBody.put("accessKey", accessKey);
        requestBody.put("requestId", requestId);
        requestBody.put("amount", amount);
        requestBody.put("orderId", orderId);
        requestBody.put("orderInfo", orderInfo);
        requestBody.put("redirectUrl", redirectUrl);
        requestBody.put("ipnUrl", ipnUrl);
        requestBody.put("extraData", extraData);
        requestBody.put("requestType", requestType);
        requestBody.put("signature", signature);
        requestBody.put("lang", "en");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://test-payment.momo.vn/v2/gateway/api/create");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonRequestBody, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    return responseBody;
                }
            }
        }
        return null;
    }


}