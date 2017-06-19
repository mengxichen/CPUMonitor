package com.mengxi.demo.sysdashboard.accessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.mengxi.demo.sysdashboard.tracker.SystemInfoTracker;

public class SystemInfoAccessor {

    private static SystemInfoAccessor instance = new SystemInfoAccessor();

    public static SystemInfoAccessor getInstance() {
        return instance;
    }

    public String getSystemInfoJson(boolean includeCpu, boolean includeMem, int intervalSecond) {
        SystemInfoTracker tracker = SystemInfoTracker.getInstance();

        Map<String, List<Long>> systemInfoMap = new HashMap<>();
        if (includeCpu) {
            systemInfoMap.put("CPU", tracker.getCpuUsage(intervalSecond));
        }
        if (includeMem) {
            systemInfoMap.put("Mem", tracker.getMemUsage(intervalSecond));
        }
        return new Gson().toJson(systemInfoMap);
    }

}
