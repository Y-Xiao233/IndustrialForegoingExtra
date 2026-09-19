package net.yxiao233.industrialforegoingextra.api.recipe;

import net.neoforged.neoforge.fluids.FluidStack;

public class FluidGeneratorRecipeWrapper implements IFluidGeneratorRecipeWrapper {
    private final int processingTime;
    private final int powerPerTick;
    private final FluidStack inputFluid;
    public FluidGeneratorRecipeWrapper(int processingTime, int powerPerTick, FluidStack inputFluid){
        this.processingTime = processingTime;
        this.powerPerTick = powerPerTick;
        this.inputFluid = inputFluid;
    }

    @Override
    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public int getPowerPerTick() {
        return powerPerTick;
    }

    @Override
    public FluidStack getInputFluid() {
        return inputFluid;
    }
}
