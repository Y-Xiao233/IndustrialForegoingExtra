package net.yxiao233.industrialforegoingextra.common.recipes;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.yxiao233.industrialforegoingextra.api.recipe.BaseRecipe;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import net.yxiao233.industrialforegoingextra.common.tile.FluidCraftingTableTile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ShapedRecipe extends BaseRecipe<ShapedRecipe> {
    public static final MapCodec<ShapedRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> {
        return in.group(Ingredient.CODEC.listOf(0,9).fieldOf("inputs").forGetter((o) -> {
            return o.inputs;
        }), FluidStack.CODEC.fieldOf("inputFluid").forGetter((o) -> {
            return o.inputFluid;
        }),ItemStack.CODEC.fieldOf("output").forGetter((o) -> {
            return o.output;
        })).apply(in, ShapedRecipe::new);
    });

    public List<Ingredient> inputs;
    public FluidStack inputFluid;
    public ItemStack output;

    public ShapedRecipe(List<Ingredient> inputs, FluidStack inputFluid, ItemStack output){
        this.inputs = inputs;
        this.inputFluid = inputFluid;
        this.output = output;
    }

    public boolean matches(InventoryComponent<FluidCraftingTableTile> inputs, FluidTankComponent<FluidCraftingTableTile> tank) {
        if (this.inputs != null && tank != null && this.inputFluid != null) {
            NonNullList<Boolean> matches = NonNullList.withSize(9, false);

            for (int i = 0; i < this.inputs.size(); i++) {
                Iterator<ItemStack> iterator = Arrays.stream(this.inputs.get(i).getItems()).iterator();
                boolean found = false;

                while (iterator.hasNext() && !found){
                    ItemStack stack = iterator.next();
                    if(stack.is(IFEItems.AIR.get())){
                        found = inputs.getStackInSlot(i).isEmpty();
                        matches.set(i,found);
                    }else{
                        found = inputs.getStackInSlot(i).is(stack.getItem());
                        matches.set(i,found);
                    }
                }
            }

            boolean b2 = tank.drainForced(this.inputFluid, IFluidHandler.FluidAction.SIMULATE).getAmount() == this.inputFluid.getAmount();

            return matches.stream().allMatch(i -> i) && b2;
        }else{
            return false;
        }
    }

    @Override
    public @NotNull Optional<ItemStack> getOutput() {
        return Optional.of(this.output.copy());
    }

    @Override
    public DeferredRecipe<ShapedRecipe> getDeferredRecipe() {
        return IFERecipes.SHAPED;
    }
}
