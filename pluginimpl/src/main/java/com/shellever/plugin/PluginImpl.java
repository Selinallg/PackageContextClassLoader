package com.shellever.plugin;

public class PluginImpl implements PluginIf {
    @Override
    public String getDeviceInfo() {
        return "Shellever";
    }

    @Override
    public String getExpInfo(String s) {
        return "return:"+s;
    }
}
