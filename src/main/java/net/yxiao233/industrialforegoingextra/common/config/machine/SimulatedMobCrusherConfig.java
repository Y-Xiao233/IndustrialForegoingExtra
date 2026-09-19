package net.yxiao233.industrialforegoingextra.common.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.MachineConfig;

@ConfigFile.Child(MachineConfig.class)
public class SimulatedMobCrusherConfig {
    @ConfigVal(comment = "Cooldown Time in Ticks [20 Ticks per Second] - Default: [400 (20s)]")
    public static int maxProgress = 400;
    @ConfigVal(comment = "Amount of Power Consumed per Tick - Default: [40FE]")
    public static int powerPerOperation = 40;
    @ConfigVal(comment = "Max Stored Power [FE] - Default: [10000 FE]")
    public static int maxStoredPower = 10000;
    @ConfigVal(comment = "Max Output Slot Limit - Default: [4096]")
    public static int maxOutputSlotLimit = 4096;
    @ConfigVal(comment = "Max Essence [mb] - Default: [32000 mb]")
    public static int tankSize = 32000;
}
