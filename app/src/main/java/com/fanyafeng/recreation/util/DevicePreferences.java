package com.fanyafeng.recreation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by zgw on 16/5/7 16:35.
 */
public class DevicePreferences {
    private SharedPreferences pref;
    private final static String name = "device_preference";
    private final static String deviceId = "device_id";
    private final static String mac_addr = "mac_addr";
    private final static String tokenId = "token_id";
    private final static String xg_token = "xg_token";
    private final static String new_id = "new_id";

    private Context mContext;

    public DevicePreferences(Context context) {
        pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        mContext = context;
    }

    public  void putXGToken(String xgToken) {
        if (!pref.contains(xg_token)) {
            pref.edit().putString(xg_token, xgToken).commit();
        }
    }

    public String getXGToken() {
        return pref.getString(xg_token, "");
    }

    public String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (!pref.contains(deviceId)) {
            String id = tm.getDeviceId();
            if (id == null || id.equals("0") || id.equals("")) {
                id = UUID.randomUUID().toString();
            }
            pref.edit().putString(deviceId, id).commit();
        }
        return pref.getString(deviceId, tm.getDeviceId());
    }


    public String getMacAddr() {
        if (!pref.contains(mac_addr)) {
            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info.getMacAddress() != null) {
                pref.edit().putString(mac_addr, info.getMacAddress()).commit();
            } else {
                pref.edit().putString(mac_addr, UUID.randomUUID().toString()).commit();
            }
        }
        return pref.getString(mac_addr, null);
    }

    public String getTokenId() {
        if (!pref.contains(tokenId)) {
            pref.edit().putString(tokenId, UUID.randomUUID().toString()).commit();
        }
        return pref.getString(tokenId, "");
    }

    public String getUADeviceID() {
        return MD5Transfer.MD5(getDeviceId() + getMacAddr()) + "_" + getNewID();
    }

    private String getNewID() {
        if (!pref.contains(new_id)) {
            pref.edit().putString(new_id, RandomStringUtils.random(6, true, true)).commit();
        }
        return pref.getString(new_id, "");
    }

    public void clearUADeviceID() {
        pref.edit().remove(new_id).commit();
    }
}
