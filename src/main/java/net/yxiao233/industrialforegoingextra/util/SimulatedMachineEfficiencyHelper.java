package net.yxiao233.industrialforegoingextra.util;

import net.yxiao233.industrialforegoingextra.common.config.machine.SimulatedMachineConfig;

public class SimulatedMachineEfficiencyHelper {
    public static final int BASE = SimulatedMachineConfig.base;
    public static final double RATIO = SimulatedMachineConfig.ratio;
    public static final int MAX_MULTIPLE = SimulatedMachineConfig.maxEfficiency;
    public static int getMultiple(double efficiency, int addonTier) {
        long threshold = 0;
        long step = BASE;
        int multiple = 0;

        while (multiple < MAX_MULTIPLE && efficiency >= threshold + step) {
            threshold += step;
            step = (long) (step * RATIO);
            multiple++;
        }
        return Math.max(1,multiple) * addonTier;
    }
}
