package net.yxiao233.industrialforegoingextra.api.fluid.fluid;

import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BaseFluidInstance {
    private final DeferredHolder<FluidType, FluidType> fluidType;
    private final DeferredHolder<Fluid, Fluid> flowingFluid;
    private final DeferredHolder<Fluid, Fluid> sourceFluid;
    private final DeferredHolder<Item, Item> bucketFluid;
    private final DeferredHolder<Block, Block> blockFluid;

    private final String fluidName;

    public BaseFluidInstance(
            DeferredRegistryHelper helper,
            String name,
            FluidType.Properties fluidTypeProperties,
            IClientFluidTypeExtensions renderProperties
    ) {
        this.fluidName = name;

        this.fluidType = helper.registerGeneric(NeoForgeRegistries.FLUID_TYPES.key(),name, () ->
                new FluidType(fluidTypeProperties) {
                    @Override
                    @SuppressWarnings("removal")
                    public void initializeClient(@NotNull Consumer<IClientFluidTypeExtensions> consumer) {
                        consumer.accept(renderProperties);
                    }
                });

        this.sourceFluid = helper.registerGeneric(Registries.FLUID,name + "_source", () -> new Source(this));

        this.flowingFluid = helper.registerGeneric(Registries.FLUID,name + "_flowing", () -> new Flowing(this));

        this.blockFluid = helper.registerGeneric(Registries.BLOCK,name,
                () -> new LiquidBlock(
                        (FlowingFluid) sourceFluid.get(),
                        BlockBehaviour.Properties.of()
                                .replaceable()
                                .noCollission()
                                .strength(100f)
                                .pushReaction(PushReaction.DESTROY)
                                .liquid().sound(SoundType.EMPTY)
                                .noLootTable()
                ));

        this.bucketFluid = helper.registerGeneric(Registries.ITEM, name + "_bucket", () -> {
            BucketItem item = new BucketItem(this.sourceFluid.get(), (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));
            IndustrialForegoingExtra.TAB.getTabList().add(item);
            return item;
        });
    }

    public DeferredHolder<FluidType,FluidType> getFluidType() {
        return fluidType;
    }

    public DeferredHolder<Fluid,Fluid> getFlowingFluid() {
        return flowingFluid;
    }

    public DeferredHolder<Fluid,Fluid> getSourceFluid() {
        return sourceFluid;
    }


    public Item getBucketFluid() {
        return bucketFluid.get();
    }

    public Block getBlockFluid() {
        return blockFluid.get();
    }

    public String getFluidName() {
        return fluidName;
    }


    public static class Source extends BaseFluid {
        public Source(BaseFluidInstance instance) {
            super(instance);
        }

        @Override
        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return true;
        }
    }


    public static class Flowing extends BaseFluid {

        public Flowing(BaseFluidInstance instance) {
            super(instance);
            this.registerDefaultState(
                    this.getStateDefinition().any().setValue(LEVEL, 7)
            );
        }

        @Override
        protected void createFluidStateDefinition(
                StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(@NotNull FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return false;
        }
    }
}
