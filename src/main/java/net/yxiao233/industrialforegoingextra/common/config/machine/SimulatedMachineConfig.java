package net.yxiao233.industrialforegoingextra.common.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.MachineConfig;

@ConfigFile.Child(MachineConfig.class)
public class SimulatedMachineConfig {
    @ConfigVal(comment = "Basic data required for efficiency growth - Default: [1000]")
    public static final int base = 1000;
    @ConfigVal(comment = "Growth multiplier - Default: [2]")
    public static final double ratio = 2.0;
    @ConfigVal(comment = "Maximum efficiency - Default: [100]")
    public static final int maxEfficiency = 100;
}
