package org.mcwonderland.uhc.util;

import org.mineacademy.fo.MinecraftVersion;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

public class RuntimeUtil {

    private static final DecimalFormat format = new DecimalFormat("00.00");
    private static Class<?> clazz = null;
    private static Object si = null;
    private static Field tpsField = null;
    private static final Runtime rt = Runtime.getRuntime();
    private static final int fillMemoryTolerance = 500;

    static {
        try {
            clazz = Class.forName("net.minecraft.server." + MinecraftVersion.getServerVersion() + "." + "MinecraftServer");
            si = clazz.getMethod("getServer").invoke(null);
            tpsField = si.getClass().getField("recentTps");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getTPS(int time) {
        double[] tps = null;
        try {
            tps = ((double[]) tpsField.get(si));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert tps != null;
        return tps[time];
    }

    public static DecimalFormat getTPSFormat() {
        return format;
    }

    public static long Now() {
        return System.currentTimeMillis();
    }

    public static int AvailableMemory() {
        return (int) ((rt.maxMemory() - rt.totalMemory() + rt.freeMemory()) / 1048576);
    }

    public static boolean AvailableMemoryTooLow() {
        return AvailableMemory() < fillMemoryTolerance;
    }


}
