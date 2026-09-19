package net.yxiao233.industrialforegoingextra.api.data;

import net.yxiao233.industrialforegoingextra.common.config.tool.AdvancedSimulatedCardConfig;

public class AdvancedSimulatedCard extends SimulatedCard implements ISimulatedCard{
    @Override
    public int getMaxMobDataStorage() {
        return AdvancedSimulatedCardConfig.maxMobDataStorage;
    }

    @Override
    public int getMaxFluidDataStorage() {
        return AdvancedSimulatedCardConfig.maxFluidDataStorage;
    }

    @Override
    public int getMaxOreDataStorage() {
        return AdvancedSimulatedCardConfig.maxOreDataStorage;
    }
}
