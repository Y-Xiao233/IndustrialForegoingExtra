package net.yxiao233.industrialforegoingextra.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.api.tile.FluidTankElements;
import net.yxiao233.industrialforegoingextra.api.tile.IFEFluidFuelGeneratorTile;
import net.yxiao233.industrialforegoingextra.common.config.machine.DragonGeneratorConfig;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEFluids;
import org.jetbrains.annotations.NotNull;

public class DragonGeneratorTile extends IFEFluidFuelGeneratorTile<DragonGeneratorTile> {
    public static final FluidStack FLUID_FUEL = new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(), 1000);

    public DragonGeneratorTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.DRAGON_GENERATOR, blockPos, blockState);
    }

    @Override
    public boolean canIncrease() {
        return isCurrentBlockAbove();
    }

    @Override
    public FluidTankElements getFluidTankElements() {
        return new FluidTankElements();
    }

    @Override
    public Class<?> getGeneratorConfigClass() {
        return DragonGeneratorConfig.class;
    }

    @Override
    public FluidStack getConsumeFuel() {
        return FLUID_FUEL;
    }

    @Override
    public boolean extraStartCondition() {
        return isCurrentBlockAbove();
    }

    public boolean isCurrentBlockAbove(){
        if(level == null){
            return false;
        }
        return level.getBlockState(this.getBlockPos().above()).is(Blocks.DRAGON_EGG);
    }

    @NotNull
    @Override
    public DragonGeneratorTile getSelf() {
        return this;
    }
}
