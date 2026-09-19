package net.yxiao233.industrialforegoingextra.common.recipes;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.yxiao233.industrialforegoingextra.api.recipe.BaseRecipe;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class InfuserRecipe extends BaseRecipe<InfuserRecipe> {
    public static final MapCodec<InfuserRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> {
        return in.group(ItemStack.CODEC.fieldOf("input").forGetter((o) -> {
            return o.input;
        }), FluidStack.CODEC.fieldOf("inputFluid").forGetter((o) -> {
            return o.inputFluid;
        }), Codec.INT.fieldOf("processingTime").forGetter((o) -> {
            return o.processingTime;
        }), ItemStack.CODEC.fieldOf("output").forGetter((o) -> {
            return o.output;
        })).apply(in, InfuserRecipe::new);
    });

    public ItemStack input;
    public FluidStack inputFluid;
    public int processingTime;
    public ItemStack output;
    public InfuserRecipe() {

    }

    public InfuserRecipe(ItemStack input, FluidStack inputFluid, int processingTime, ItemStack output){
        this.input = input;
        this.inputFluid = inputFluid;
        this.processingTime = processingTime;
        this.output = output;
    }

    public boolean matches(IItemHandler handler, FluidTankComponent<?> tank){
        if (input == null && tank == null && inputFluid == null) return false;

        if(!ItemStack.isSameItem(handler.getStackInSlot(0),input)){
            return false;
        }
        return tank.drainForced(inputFluid, IFluidHandler.FluidAction.SIMULATE).getAmount() == inputFluid.getAmount();
    }

    @Override
    public @NotNull Optional<ItemStack> getOutput() {
        return Optional.of(output);
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        return this.output.copy();
    }
    @Override
    public DeferredRecipe<InfuserRecipe> getDeferredRecipe() {
        return IFERecipes.INFUSER;
    }
}
