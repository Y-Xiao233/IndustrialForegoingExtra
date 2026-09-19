package net.yxiao233.industrialforegoingextra.api.recipe;

import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidGeneratorRecipeWrapper {
    int getProcessingTime();
    int getPowerPerTick();
    FluidStack getInputFluid();
}
