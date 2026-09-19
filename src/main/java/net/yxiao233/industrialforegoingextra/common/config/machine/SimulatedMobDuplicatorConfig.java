package net.yxiao233.industrialforegoingextra.common.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.MachineConfig;

@ConfigFile.Child(MachineConfig.class)
public class SimulatedMobDuplicatorConfig {
    @ConfigVal(comment = "Amount of Power Consumed per Operation - Default: [5000FE]")
    public static int powerPerOperation = 5000;
    @ConfigVal(comment = "Cooldown Time in Ticks [20 Ticks per Second] - Default: [100 (5s)]")
    public static int maxProgress = 100;
    @ConfigVal(comment = "Essence needed to spawn [Mob health*EssenceNeeded] - Default: [12]")
    public static int essenceNeeded = 12;
    @ConfigVal(comment = "Max Stored Power [FE] - Default: [10000 FE]")
    public static int maxStoredPower = 10000;
    @ConfigVal(comment = "Max Essence [mb] - Default: [8000 mb]")
    public static int tankSize = 8000;
}
