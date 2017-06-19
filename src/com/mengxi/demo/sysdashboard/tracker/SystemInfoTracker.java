package com.mengxi.demo.sysdashboard.tracker;

import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.sun.management.OperatingSystemMXBean;

public class SystemInfoTracker {

    private static SystemInfoTracker instance = new SystemInfoTracker();

    public static SystemInfoTracker getInstance() {
       return instance;
    }

    private ConcurrentLinkedQueue<Long> cpuUsage;
    private ConcurrentLinkedQueue<Long> memUsage;

    private final static int MAX_DATA_POINT_COUNT = 100;
    private final static int DATA_POINT_COUNT = 10;

    private SystemInfoTracker() {
        cpuUsage = new ConcurrentLinkedQueue<>();
        memUsage = new ConcurrentLinkedQueue<>();
        startTrackSystemResourceUsage();
    }

    public List<Long> getMemUsage(int intervalSecond) {
        Long[] memUsageArray = new Long[MAX_DATA_POINT_COUNT];
        memUsageArray = memUsage.toArray(memUsageArray);
        int memUsageCount = memUsage.size();

        LinkedList<Long> result = new LinkedList<>();
        for (int i = memUsageCount - 1, j = 0; i >= 0 && j < DATA_POINT_COUNT; i -= intervalSecond, ++j) {
            result.addFirst(memUsageArray[i]);
        }
        
        return result;
    }

    public List<Long> getCpuUsage(int intervalSecond) {
        Long[] cpuUsageArray = new Long[MAX_DATA_POINT_COUNT];
        cpuUsageArray = cpuUsage.toArray(cpuUsageArray);
        int cpuUsageCount = cpuUsage.size();

        LinkedList<Long> result = new LinkedList<>();
        for (int i = cpuUsageCount - 1, j = 0; i >= 0 && j < DATA_POINT_COUNT; i -= intervalSecond, ++j) {
            result.addFirst((Long) cpuUsageArray[i]);
        }
        return result;
    }

    private void startTrackSystemResourceUsage() {
        new Thread(new Runnable() {

            private final static long TIME_INTERVAL = 1000L;

            private final OperatingSystemMXBean operatingSystemMXBean =
                    (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            private long getCpuUsageFromLinux(){
                double cpuPercent = operatingSystemMXBean.getSystemCpuLoad() * 100.0;
                return Math.round(cpuPercent);
            }

            private long getMemUsageFromLinux(){
                long memTotal = operatingSystemMXBean.getTotalPhysicalMemorySize();
                long memFree = operatingSystemMXBean.getFreePhysicalMemorySize();
                double memPercent = (double) (memTotal - memFree) / memTotal * 100.0;
                return Math.round(memPercent);
            }

            @Override
            public void run() {
                try {
                     while (true) {
                        if (cpuUsage.size() >= MAX_DATA_POINT_COUNT) {
                            cpuUsage.poll();
                        }
                        cpuUsage.offer(Long.valueOf(getCpuUsageFromLinux()));

                        if (memUsage.size() >= MAX_DATA_POINT_COUNT) {
                            memUsage.poll();
                        }
                        memUsage.offer(Long.valueOf(getMemUsageFromLinux()));

                        Thread.sleep(TIME_INTERVAL);
                     }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
