package net.yxiao233.industrialforegoingextra.common.config.tool;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.ToolConfig;

@ConfigFile.Child(ToolConfig.class)
public class SimulatedCardConfig {
    @ConfigVal(comment = "The maximum storage capacity of the simulated card for mob data - Default: [1]")
    public static final int maxMobDataStorage = 1;
    @ConfigVal(comment = "The maximum storage capacity of the simulated card for ore data - Default: [4]")
    public static final int maxOreDataStorage = 4;
    @ConfigVal(comment = "The maximum storage capacity of the simulated card for fluid data - Default: [1]")
    public static final int maxFluidDataStorage = 1;
}
