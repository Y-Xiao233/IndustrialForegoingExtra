package net.yxiao233.industrialforegoingextra.common.recipes;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.recipe.BaseRecipe;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ArcaneDragonEggForgingRecipe extends BaseRecipe<ArcaneDragonEggForgingRecipe> {
    public static final MapCodec<ArcaneDragonEggForgingRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> {
        return in.group(ItemStack.CODEC.fieldOf("input").forGetter((o) -> {
            return o.input;
        }), FluidStack.CODEC.fieldOf("inputFluid1").forGetter((o) -> {
            return o.inputFluid1;
        }), FluidStack.CODEC.fieldOf("inputFluid2").forGetter((o) -> {
            return o.inputFluid2;
        }), Codec.INT.fieldOf("processingTime").forGetter((o) -> {
            return o.processingTime;
        }), ItemStack.CODEC.optionalFieldOf("output").forGetter((o) -> {
            return o.output;
        }), FluidStack.CODEC.optionalFieldOf("outputFluid").forGetter((o) -> {
            return o.outputFluid;
        })).apply(in, ArcaneDragonEggForgingRecipe::new);
    });
    public ItemStack input;
    public FluidStack inputFluid1;
    public FluidStack inputFluid2;
    public int processingTime;
    public Optional<ItemStack> output;
    public Optional<FluidStack> outputFluid;
    public ArcaneDragonEggForgingRecipe() {}

    public ArcaneDragonEggForgingRecipe(ItemStack input, FluidStack inputFluid1, FluidStack inputFluid2, int processingTime, Optional<ItemStack> output, Optional<FluidStack> outputFluid){
        this.input = input;
        this.inputFluid1 = inputFluid1;
        this.inputFluid2 = inputFluid2;
        this.processingTime = processingTime;
        this.output = output;
        this.outputFluid = outputFluid;
    }

    public boolean matches(IItemHandler handler, FluidTankComponent<?> tank1, FluidTankComponent<?> tank2){
        if (input == null && tank1 == null && tank2 == null && inputFluid1 == null && inputFluid2 == null) return false;

        if(!ItemStack.isSameItem(handler.getStackInSlot(0),input)){
            return false;
        }

        boolean if1 = tank1.drainForced(inputFluid1, IFluidHandler.FluidAction.SIMULATE).getAmount() == inputFluid1.getAmount();
        boolean if2 = tank2.drainForced(inputFluid2, IFluidHandler.FluidAction.SIMULATE).getAmount() == inputFluid2.getAmount();
        return if1 && if2;
    }

    @Override
    public @NotNull Optional<ItemStack> getOutput() {
        return this.output.isEmpty() ? Optional.empty() : Optional.of(this.output.orElse(ItemStack.EMPTY).copy());
    }

    @Override
    public DeferredRecipe<ArcaneDragonEggForgingRecipe> getDeferredRecipe() {
        return IFERecipes.ARCANE_DRAGON_EGG_FORGING;
    }
}
