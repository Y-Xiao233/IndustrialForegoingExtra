package net.yxiao233.industrialforegoingextra.common.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.MachineConfig;

@ConfigFile.Child(MachineConfig.class)
public class FluidCraftingTableConfig {
    @ConfigVal(comment = "Max Amount of Stored Fluid [Input] - Default: [10000mB]")
    public static int maxInputTankSize = 10000;
}