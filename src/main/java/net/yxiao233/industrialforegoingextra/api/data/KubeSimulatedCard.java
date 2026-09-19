package net.yxiao233.industrialforegoingextra.api.data;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.yxiao233.industrialforegoingextra.common.config.tool.SimulatedCardConfig;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KubeSimulatedCard extends Item implements ISimulatedCard{
    private final int maxFluidDataStorage;
    private final int maxMobDataStorage;
    private final int maxOreDataStorage;
    public KubeSimulatedCard(int maxFluidDataStorage, int maxMobDataStorage, int maxOreDataStorage) {
        super(new Properties().stacksTo(1));
        this.maxFluidDataStorage = maxFluidDataStorage;
        this.maxMobDataStorage = maxMobDataStorage;
        this.maxOreDataStorage = maxOreDataStorage;
    }

    @Override
    public int getMaxFluidDataStorage() {
        return maxFluidDataStorage;
    }

    @Override
    public int getMaxMobDataStorage() {
        return maxMobDataStorage;
    }

    @Override
    public int getMaxOreDataStorage() {
        return maxOreDataStorage;
    }

    public void setType(ItemStack card, String type){
        if(!card.has(IFEDataComponents.DATA_TYPE)){
            card.set(IFEDataComponents.DATA_TYPE, type);
        }
    }

    public String getType(ItemStack stack) {
        if(stack.has(IFEDataComponents.DATA_TYPE)){
            return stack.get(IFEDataComponents.DATA_TYPE);
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltips, @NotNull TooltipFlag flag) {
        if(stack.has(IFEDataComponents.MOB_DATA)){
            MobData mobData = stack.get(IFEDataComponents.MOB_DATA);
            if(mobData != null){
                mobData.appendHoverTip(tooltips);
            }
        }else if(stack.has(IFEDataComponents.ORE_DATA)){
            OreData oreData = stack.get(IFEDataComponents.ORE_DATA);
            if(oreData != null){
                oreData.appendHoverTip(Minecraft.getInstance().level.registryAccess(),tooltips);
            }
        }else if(stack.has(IFEDataComponents.FLUID_DATA)){
            FluidData fluidData = stack.get(IFEDataComponents.FLUID_DATA);
            if(fluidData != null){
                fluidData.appendHoverTip(Minecraft.getInstance().level.registryAccess(),tooltips);
            }
        }
    }
}
