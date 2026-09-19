package net.yxiao233.industrialforegoingextra.common.fluid;

import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.yxiao233.industrialforegoingextra.api.fluid.fluid.AnimateFluid;
import net.yxiao233.industrialforegoingextra.api.fluid.fluid.AnimateFluidInstance;
import net.yxiao233.industrialforegoingextra.common.config.misc.LiquidDragonBreathConfig;
import org.jetbrains.annotations.NotNull;

public class LiquidDragonBreathInstance extends AnimateFluidInstance {
    public LiquidDragonBreathInstance(DeferredRegistryHelper helper, String fluid, FluidType.Properties fluidTypeProperties, IClientFluidTypeExtensions renderProperties) {
        super(helper, fluid, fluidTypeProperties, renderProperties);
    }

    @Override
    public AnimateFluid.Source<?> setSource(AnimateFluidInstance abstractAnimateFluidInstance) {
        return new LiquidDragonBreathFluid.Source(this);
    }

    @Override
    public AnimateFluid.Flowing<?> setFlowing(AnimateFluidInstance abstractAnimateFluidInstance) {
        return new LiquidDragonBreathFluid.Flowing(this);
    }

    public static class LiquidDragonBreathFluid extends AnimateFluid {

        public LiquidDragonBreathFluid(AnimateFluidInstance abstractAnimateFluidInstance) {
            super(abstractAnimateFluidInstance);
        }

        public static void tick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource randomSource){
            BlockPos blockpos = blockPos.above();
            if (level.getBlockState(blockpos).isAir() && !level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                if (LiquidDragonBreathConfig.isFluidGenerateParticles && randomSource.nextInt((int)(1 / LiquidDragonBreathConfig.ProbabilityOfParticleGeneration)) == 0) {
                    double d0 = (double)blockPos.getX() + randomSource.nextDouble();
                    double d1 = (double)blockPos.getY() + 1.0;
                    double d2 = (double)blockPos.getZ() + randomSource.nextDouble();
                    level.addParticle(ParticleTypes.DRAGON_BREATH, d0, d1, d2, 0.0, 0.0, 0.0);
                    level.playLocalSound(d0, d1, d2, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.2F + randomSource.nextFloat() * 0.2F, 0.9F + randomSource.nextFloat() * 0.15F, false);
                }

                if (LiquidDragonBreathConfig.isFluidProduceSound && randomSource.nextInt((int)(1 / LiquidDragonBreathConfig.ProbabilityOfProducingSound)) == 0) {
                    level.playLocalSound(blockPos, SoundEvents.ENDER_DRAGON_AMBIENT, SoundSource.BLOCKS, 0.1F + randomSource.nextFloat() * 0.1F, 0.1F + randomSource.nextFloat() * 0.1F, false);
                }
            }
        }

        @Override
        public void animateTick(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull FluidState fluidState, @NotNull RandomSource randomSource) {
            LiquidDragonBreathFluid.tick(level,blockPos,fluidState,randomSource);
        }

        public static class Source extends AnimateFluid.Source<LiquidDragonBreathInstance>{

            public Source(LiquidDragonBreathInstance instance) {
                super(instance);
            }

            @Override
            public void animateTick(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull FluidState fluidState, @NotNull RandomSource randomSource) {
                LiquidDragonBreathFluid.tick(level,blockPos,fluidState,randomSource);
            }
        }


        public static class Flowing extends AnimateFluid.Flowing<LiquidDragonBreathInstance>{

            public Flowing(LiquidDragonBreathInstance instance) {
                super(instance);
            }

            @Override
            public void animateTick(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull FluidState fluidState, @NotNull RandomSource randomSource) {
                LiquidDragonBreathFluid.tick(level,blockPos,fluidState,randomSource);
            }
        }
    }
}
