package net.yxiao233.industrialforegoingextra.api.data;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;

public interface ISimulatedCard {
    default int getMaxMobDataStorage(){
        return 1;
    }

    default int getMaxOreDataStorage(){
        return 4;
    }

    default int getMaxFluidDataStorage(){
        return 1;
    }

    default boolean isDataEmpty(ItemStack stack){
        if(stack.getItem() instanceof SimulatedCard){
            return !stack.has(IFEDataComponents.DATA_TYPE);
        }
        return true;
    }

    default boolean is(ItemStack stack, String type){
        if(stack.getItem() instanceof SimulatedCard card){
            if(isDataEmpty(stack)){
                return false;
            }else{
                return type != null && card.getType(stack).equals(type);
            }
        }
        return false;
    }
    default void createMobData(ItemStack card, LivingEntity entity){
        if(entity != null && card.getItem() instanceof SimulatedCard simulatedCard){
            simulatedCard.setType(card,"mob");
            card.set(IFEDataComponents.MOB_DATA,MobData.create(entity));
        }
    }

    default void updateMobData(ItemStack card, LivingEntity entity, double delta){
        if(card.getItem() instanceof ISimulatedCard simulatedCard){
            if (card.has(IFEDataComponents.MOB_DATA)) {
                MobData mobData = card.get(IFEDataComponents.MOB_DATA);
                if(mobData != null){
                    if(mobData.contains(entity)){
                        mobData.update(card,entity,delta);
                    }else if(simulatedCard.getMaxMobDataStorage() > mobData.getEntitySize()){
                        mobData.put(card,entity,1);
                    }
                }
            }
        }
    }


    default void createOreData(Level level, ItemStack card, ItemStack ore){
        if(!ore.isEmpty() && card.getItem() instanceof SimulatedCard simulatedCard){
            simulatedCard.setType(card,"ore");
            card.set(IFEDataComponents.ORE_DATA,OreData.create(level,ore));
        }
    }

    default void updateOreData(Level level,ItemStack card, ItemStack ore, double delta){
        if(card.getItem() instanceof ISimulatedCard simulatedCard){
            if (card.has(IFEDataComponents.ORE_DATA)) {
                OreData oreData = card.get(IFEDataComponents.ORE_DATA);
                if(oreData != null){
                    if(oreData.contains(ore)){
                        oreData.update(card,ore,delta);
                    }else if(simulatedCard.getMaxOreDataStorage() > oreData.getOreSize()){
                        oreData.put(level,card,ore,1);
                    }
                }
            }
        }
    }

    default void createFluidData(Level level, ItemStack card, FluidStack fluidStack){
        if(!fluidStack.isEmpty() && card.getItem() instanceof SimulatedCard simulatedCard){
            simulatedCard.setType(card,"fluid");
            card.set(IFEDataComponents.FLUID_DATA,FluidData.create(level,fluidStack));
        }
    }

    default void updateFluidData(Level level,ItemStack card, FluidStack fluidStack, double delta){
        if(card.getItem() instanceof ISimulatedCard simulatedCard){
            if (card.has(IFEDataComponents.FLUID_DATA)) {
                FluidData fluidData = card.get(IFEDataComponents.FLUID_DATA);
                if(fluidData != null){
                    if(fluidData.contains(fluidStack)){
                        fluidData.update(card,fluidStack,delta);
                    }else if(simulatedCard.getMaxFluidDataStorage() > fluidData.getFluidSize()){
                        fluidData.put(level,card,fluidStack,1);
                    }
                }
            }
        }
    }
}
