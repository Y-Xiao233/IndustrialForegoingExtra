package net.yxiao233.industrialforegoingextra.common.config.tool;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.industrialforegoingextra.common.config.ToolConfig;

@ConfigFile.Child(ToolConfig.class)
public class AdvancedMobImprisonmentToolConfig {
    @ConfigVal(comment = "If true, only the player who tamed the animal can capture it.")
    public static boolean onlyOwnerCanCapture = false;
}
