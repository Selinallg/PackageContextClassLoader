package com.shellever.dexclassloader;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.meituan.android.walle.WalleChannelReader;

/**
 * 读取设置的 渠道相关信息
 */
public class ChannelHelper {


    public static String getChannel(Context context) {
        if (TextUtils.isEmpty(WalleChannelReader.getChannel(context))) {
            return "official";//默认渠道
        }
        return WalleChannelReader.getChannel(context);
    }

    public static String getMsg(Context context, String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        if (TextUtils.isEmpty(WalleChannelReader.get(context, key))) {
            return "";//默认信息
        }
        // 或者也可以直接根据key获取
        return WalleChannelReader.get(context, key);
    }


    public static String setMsg(Context context, String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        if (TextUtils.isEmpty(WalleChannelReader.get(context, key))) {
            return "";//默认信息
        }
        // 或者也可以直接根据key获取
        return WalleChannelReader.get(context, key);
    }




//        6，硬件信息 刘良国提供接口给张东
//    包含：设备厂商信息、设备型号、设备序列号

    /**
     * @return 设备厂商信息
     */
    public static String getDevManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * @return 设备型号
     */
    public static String getDevModel() {
        return Build.MODEL;
    }
    /**
     * @return 设备序列号
     */
    public static String getDevSerial() {
        return Build.SERIAL;
    }

    // adb shell getprop ro.product.name
    // sdm845
    public static String getDevProduct() {
        return Build.PRODUCT;
    }
    // qcom
    // adb shell getprop ro.hardware
    public static String getDevHardware() {
        return Build.HARDWARE;
    }

    // adb shell getprop ro.product.board
    // sdm845
    public static String getBoard() {
        return Build.BOARD;
    }

    // adb shell getprop ro.product.brand
    // Android
    public static String getDevBrand() {
        return Build.BRAND;
    }

    // adb shell getprop ro.build.display.id
    // OPM1.171019.026 release-keys
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    // adb shell getprop ro.product.device
    // sdm845
    public static String getDevice() {
        return Build.DEVICE;
    }


    // adb shell getprop ro.build.type
    // user
    public static String getDevType() {
        return Build.TYPE;
    }

    // adb shell getprop ro.build.tags
    // release-keys
    public static String getDevTags() {
        return Build.TAGS;
    }


}
