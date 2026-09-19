package net.yxiao233.industrialforegoingextra.common.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.MachineConfig;

@ConfigFile.Child(MachineConfig.class)
public class SimulatedFluidLaserBaseConfig {
    @ConfigVal(comment = "Max progress of the machine")
    public static int maxProgress = 400;
    @ConfigVal(comment = "Amount of Power Consumed per Tick - Default: [40FE]")
    public static int powerPerOperation = 40;
    @ConfigVal(comment = "Max Stored Power [FE] - Default: [10000 FE]")
    public static int maxStoredPower = 10000;

    @ConfigVal(comment = "Max Tank Size [mb] - Default: [32000 mb]")
    public static int tankSize = 32000;
}
