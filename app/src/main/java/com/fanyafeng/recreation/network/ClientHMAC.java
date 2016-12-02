package com.fanyafeng.recreation.network;

import android.net.Uri;


import com.fanyafeng.recreation.util.StringUtil;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ClientHMAC {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static final int DEFAULT_FACTOR = 1000000;

    /**
     * @param url      uri + queryString
     * @param deviceId 客户端deviceid
     * @param token    服务器传递的token
     * @return 返回headers信息
     */
    public static Map<String, String> hmacInfos(String url, String deviceId, String token) {

        Uri uri = Uri.parse(url);

        if (uri.getQuery() == null) {
            url = uri.getPath();
        } else {
            url = uri.getPath() + "?" + uri.getQuery();
        }

        Map<String, String> hmacInfos = new HashMap<String, String>();

        long ts = System.currentTimeMillis();
        long nonce = ts * DEFAULT_FACTOR;

        String ts_nonce = ts + "+" + nonce;
//        String data = url.replace("\t", "").replace("\n", "").replace("\f", "").replace("\r", "") + "+" + ts_nonce;
        //FIXME: hmac issue
        String data = url + "+" + ts_nonce;

        String x_req_sig;
        try {
            x_req_sig = calculateHMAC(data, deviceId);

        } catch (SignatureException e) {
            x_req_sig = "";
        }

        //客户端使用这三个返回的项作为http header放到请求里
        if (!StringUtil.isNullOrEmpty(token)) {
            hmacInfos.put("x-vino-http-key", token);
        }
        hmacInfos.put("x-ts-header", ts_nonce);
        hmacInfos.put("x-req-sig", x_req_sig);

        return hmacInfos;
    }

    /**
     * 输出HMAC_SHA1数字签名
     *
     * @param data 将要签名的数据
     * @return BASE64加密后的HMAC签名
     * @throws java.security.SignatureException
     */
    private static String calculateHMAC(String data, String key)
            throws java.security.SignatureException {
        String result;
        try {

            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
//			result = Base64.encodeBase64String(rawHmac);
            result = new String(Base64.encode(rawHmac));

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}