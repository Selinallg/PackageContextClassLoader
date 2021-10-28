package com.shellever.dexclassloader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import gsxr.nvr.android.channel.GSXRChannelReader;
import gsxr.nvr.android.channel.utils.APKVersionCodeUtils;
import gsxr.nvr.android.channel.utils.SignatureUtils;
import gsxr.nvr.android.channel.utils.SystemPropertiesAssistant;

/**
 * 读取设置的 渠道相关信息
 */
public class ChannelHelper {


    public static String getChannel(Context context) {
        if (TextUtils.isEmpty(GSXRChannelReader.getChannel(context))) {
            return "official";//默认渠道
        }
        return GSXRChannelReader.getChannel(context);
    }

    public static String getMsg(Context context, String key) {
        Log.d("ChannelHelper", "getMsg: key=" + key);
        if (TextUtils.isEmpty(key)) {
            return "";
        }
//        if (TextUtils.isEmpty(GSXRChannelReader.get(context, key))) {
//            return "";//默认信息
//        }
        // 或者也可以直接根据key获取
        String v2Msg = GSXRChannelReader.get(context, key);
        if (!TextUtils.isEmpty(v2Msg)) {
            Log.d("ChannelHelper", "getMsg: return v2 msg");
            return v2Msg;
        } else {
            Log.d("ChannelHelper", "getMsg: return v1 msg");
            String v1Msg = getV1Msg(context, key);
            if (!TextUtils.isEmpty(v1Msg)) {
                return v1Msg;
            }
            return "";
        }
    }

//     4，package 信息
//    包含：packagename

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    //    包含：versioncode、versionname、签名信息
    public static int getPackageVersionCode(Context context) {
        return APKVersionCodeUtils.getVersionCode(context);
    }

    //    包含：versionname、签名信息
    public static String getPackageVersionName(Context context) {
        return APKVersionCodeUtils.getVerName(context);
    }

    /**
     * @param context
     * @param type    String SHA1 = "SHA1";
     *                String MD5 = "MD5";
     * @return
     */
    //    包含：签名信息
    public static String getSingInfo(Context context, String type) {
        return SignatureUtils.getSingInfo(context, context.getPackageName(), type);
    }

//    5，ROM 信息
//    包含：版本信息

    // adb shell getprop ro.build.version.vr.level
    // VR1235
    public static String getVROSLevel() {
        return SystemPropertiesAssistant.get("ro.build.version.vr.level", null);
    }


    // adb shell getprop ro.build.version.release.date
    // 20210805102054
    public static String getVROSReleaseDate() {
        return SystemPropertiesAssistant.get("ro.build.version.release.date", null);
    }


    // adb shell getprop ro.product.system.name
    // NOLO-UI
    // adb shell getprop ro.build.version.vr.release
    // 1.00.068
    public static String getVROSVersion() {
        return SystemPropertiesAssistant.get("ro.product.system.name", "NOLO-UI") + " " + SystemPropertiesAssistant.get("ro.build.version.vr.release", "1.00.0");
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

    /**
     * V1 签名，需要用户自己在application 节点先，打包
     * <meta-data
     * android:name="key"
     * android:value="value" />
     *
     * @param ctx
     * @param key
     * @return
     */
    public static String getV1Msg(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }


}
