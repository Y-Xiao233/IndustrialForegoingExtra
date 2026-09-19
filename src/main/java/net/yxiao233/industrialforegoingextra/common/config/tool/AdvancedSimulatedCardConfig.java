package net.yxiao233.industrialforegoingextra.common.config.tool;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.ToolConfig;

@ConfigFile.Child(ToolConfig.class)
public class AdvancedSimulatedCardConfig {
    @ConfigVal(comment = "The maximum storage capacity of the advanced simulated card for mob data - Default: [4]")
    public static final int maxMobDataStorage = 4;
    @ConfigVal(comment = "The maximum storage capacity of the advanced simulated card for ore data - Default: [8]")
    public static final int maxOreDataStorage = 8;
    @ConfigVal(comment = "The maximum storage capacity of the advanced simulated card for fluid data - Default: [1]")
    public static final int maxFluidDataStorage = 1;
}
