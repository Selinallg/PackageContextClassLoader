package com.shellever.dexclassloader;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.shellever.plugin.PluginIf;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DexClassLoader";

    private TextView mLoaderResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoaderResultTv = findViewById(R.id.tv_loader_result);

        // 使用接口方式调用dex里面的方法
        Log.d(TAG, "testDexClassLoaderWithIf()");
        //testDexClassLoaderWithIf();

        // 使用反射方式调用dex里面的方法
        Log.d(TAG, "testDexClassLoaderWithReflect()");
        //testDexClassLoaderWithReflect();

        String devManufacturer = ChannelHelper.getDevManufacturer();
        Log.d(TAG, "onCreate: devManufacturer=" + devManufacturer);

        String devModel = ChannelHelper.getDevModel();
        Log.d(TAG, "onCreate: devModel=" + devModel);

        //testDexClassLoaderWithReflect2();
        //testDexClassLoaderWithReflect3();
        testDexClassLoaderWithReflect4();
    }

    /**
     * 反射调用静态方法，无参数
     */
    public void testDexClassLoaderWithReflect2() {

        // 加载接口具体实现的经过dex转换过的jar包
        DexClassLoader dexClassLoader = NoloLoarder.getClassLoader(this, "channel.jar");
        String         className      = "com.shellever.dexclassloader.ChannelHelper";
        Log.d(TAG, "className = " + className);


        try {
            Class  pluginDevInfoClazz = dexClassLoader.loadClass(className);
            Method localMethod        = pluginDevInfoClazz.getMethod("getDevModel",  new Class[0]);
            String returnType         = localMethod.getReturnType().getSimpleName();
            Log.d(TAG, "testDexClassLoaderWithReflect2: returnType=" + returnType);
            Object localObject = null;
            if ("String".equals(returnType)) {
                // 通过反射调用接口方法
                // localObject 如果是实例方法，为实例对象；如果是静态方法，则为null
                // 第二次参数 反射方法需要传入的参数
                String devinfo = (String) localMethod.invoke(localObject, new Object[0]);
                Log.d(TAG, "testDexClassLoaderWithReflect2: " + devinfo);
                mLoaderResultTv.append("\n" + devinfo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射调用静态方法，有参数 Context
     */
    public void testDexClassLoaderWithReflect3() {

        // 加载接口具体实现的经过dex转换过的jar包
        DexClassLoader dexClassLoader = NoloLoarder.getClassLoader(this, "channel.jar");
        String         className      = "com.shellever.dexclassloader.ChannelHelper";
        Log.d(TAG, "className = " + className);


        try {
            Class  pluginDevInfoClazz = dexClassLoader.loadClass(className);
            Method localMethod        = pluginDevInfoClazz.getMethod("getChannel", Context.class);
            String returnType         = localMethod.getReturnType().getSimpleName();
            Log.d(TAG, "testDexClassLoaderWithReflect2: returnType=" + returnType);
            Object localObject = null;
            if ("String".equals(returnType)) {
                // 通过反射调用接口方法
                // localObject 如果是实例方法，为实例对象；如果是静态方法，则为null
                // 第二次参数 反射方法需要传入的参数
                Object[] params = new Object[1];
                params[0] = this;
                String devinfo = (String) localMethod.invoke(localObject, params);
                Log.d(TAG, "testDexClassLoaderWithReflect2: " + devinfo);
                mLoaderResultTv.append("\n" + devinfo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 反射调用对象方法，有参数 Context 构造实例
     * 调用方法：传入参数  值返回
     */
    public void testDexClassLoaderWithReflect4() {

        // 加载接口具体实现的经过dex转换过的jar包
        DexClassLoader dexClassLoader = NoloLoarder.getClassLoader(this, "pluginDevInfo.jar");
        // 获取COMMON模块构造方法及参数
        String className = "com.shellever.plugin.common.Common";
        String classArgs = "Context";
        Log.d(TAG, "className = " + className);

        // 获取COMMON模块接口方法及参数
        String functionName = "getExpInfo";
        String functionArgs = "nolovr";
        Log.d(TAG, "functionName = " + functionName);
        try {
            Class pluginDevInfoClazz = dexClassLoader.loadClass(className);
            //Object localObject        = pluginDevInfoClazz.getConstructor(getParamType(classArgs)).newInstance(new Object[]{this});
            Object[] obj = new Object[1];
            obj[0] = this;
            //创建构造方法，并调用构造方法
            Object localObject = pluginDevInfoClazz.getConstructor(Context.class).newInstance(obj);
            //Method localMethod        = localObject.getClass().getDeclaredMethod(functionName, getParamType(functionArgs));
            //方法参数类型
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;
            Method localMethod = localObject.getClass().getDeclaredMethod(functionName, paramTypes);
            String returnType  = localMethod.getReturnType().getSimpleName(); // 获取方法返回值类型
            if ("String".equals(returnType)) {
                // 通过反射调用方法
                Object[] param = new Object[1];
                param[0] = functionArgs;
                String devinfo = (String) localMethod.invoke(localObject, param);
                mLoaderResultTv.append("\n" + devinfo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void testDexClassLoaderWithReflect() {
        HashMap<String, DeviceInfo> deviceInfo = parseDeviceInfo(); // 解析xml定义文件

        // 加载接口具体实现的经过dex转换过的jar包
        DexClassLoader dexClassLoader = NoloLoarder.getClassLoader(this, "pluginDevInfo.jar");
        // 获取COMMON模块构造方法及参数
        DeviceInfo localDeviceInfo = deviceInfo.get("COMMON");
        String     className       = localDeviceInfo.name;
        String     classArgs       = localDeviceInfo.args;
        Log.d(TAG, "className = " + className);

        // 获取COMMON模块接口方法及参数
        DeviceInfo localDeviceInfo2 = deviceInfo.get("COMMON" + "getDeviceInfo");
        String     functionName     = localDeviceInfo2.name;
        String     functionArgs     = localDeviceInfo2.args;
        Log.d(TAG, "functionName = " + functionName);
        try {
            Class  pluginDevInfoClazz = dexClassLoader.loadClass(className);
            Object localObject        = pluginDevInfoClazz.getConstructor(NoloLoarder.getParamType(classArgs)).newInstance(new Object[]{this});
            Method localMethod        = localObject.getClass().getDeclaredMethod(functionName, NoloLoarder.getParamType(functionArgs));
            String returnType         = localMethod.getReturnType().getSimpleName(); // 获取方法返回值类型
            if ("String".equals(returnType)) {
                // 通过反射调用接口方法
                String devinfo = (String) localMethod.invoke(localObject, new Object[0]);
                mLoaderResultTv.append("\n" + devinfo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, DeviceInfo> parseDeviceInfo() {
        HashMap<String, DeviceInfo> deviceInfo = new HashMap<>();
        XmlResourceParser           parser     = getResources().getXml(R.xml.device_class_module);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("module".equals(parser.getName())) {
                            DeviceInfo localDeviceInfo = new DeviceInfo();
                            localDeviceInfo.name = parser.getAttributeValue(null, "name");
                            localDeviceInfo.args = parser.getAttributeValue(null, "args");
                            deviceInfo.put(parser.getAttributeValue(null, "id"), localDeviceInfo);
                        }
                        if ("function".equals(parser.getName())) {
                            DeviceInfo localDeviceInfo = new DeviceInfo();
                            localDeviceInfo.name = parser.getAttributeValue(null, "name");
                            localDeviceInfo.args = parser.getAttributeValue(null, "args");
                            deviceInfo.put(parser.getAttributeValue(null, "id") + localDeviceInfo.name, localDeviceInfo);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deviceInfo;
    }

    public class DeviceInfo {
        public String args;
        public String name;
    }

    public class retResult {
        public  byte[]   bytes;
        public  String[] strings;
        private String   string;

        public retResult() {
        }
    }

    // =============================================================================================
    public void testDexClassLoaderWithIf() {
        // 加载接口具体实现的经过dex转换过的jar包
        DexClassLoader dexClassLoader = NoloLoarder.getClassLoader(this, "pluginImpl.jar");
        try {
            // 加载接口具体实现类
            Class    pluginImplClazz = dexClassLoader.loadClass("com.shellever.plugin.PluginImpl");
            PluginIf pluginIf        = (PluginIf) pluginImplClazz.newInstance();   // 直接强制类型转换
            String   devInfo         = pluginIf.getDeviceInfo();  // 调用接口方法
            mLoaderResultTv.setText(devInfo);
            Log.d(TAG, "testDexClassLoaderWithIf:devInfo= " + devInfo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "1 testDexClassLoaderWithIf: ", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "2 testDexClassLoaderWithIf: ", e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e(TAG, "3 testDexClassLoaderWithIf: ", e);
        }
    }

}
