package net.yxiao233.industrialforegoingextra.common.recipes;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.yxiao233.industrialforegoingextra.api.recipe.BaseRecipe;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import net.yxiao233.industrialforegoingextra.common.tile.FluidCraftingTableTile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ShapelessRecipe extends BaseRecipe<ShapelessRecipe> {
    public static final MapCodec<ShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> {
        return in.group(Ingredient.CODEC.listOf(0,9).fieldOf("inputs").forGetter((o) -> {
            return o.inputs;
        }),FluidStack.CODEC.fieldOf("inputFluid").forGetter((o) -> {
            return o.inputFluid;
        }),ItemStack.CODEC.fieldOf("output").forGetter((o) -> {
            return o.output;
        })).apply(in, ShapelessRecipe::new);
    });

    public List<Ingredient> inputs;
    public FluidStack inputFluid;
    public ItemStack output;

    public ShapelessRecipe(List<Ingredient> inputs, FluidStack inputFluid, ItemStack output){
        this.inputs = inputs;
        this.inputFluid = inputFluid;
        this.output = output;
    }

    public boolean matches(InventoryComponent<FluidCraftingTableTile> inputs, FluidTankComponent<FluidCraftingTableTile> tank) {
        if (this.inputs != null && tank != null && this.inputFluid != null) {
            List<ItemStack> handlerItems = new ArrayList<>();

            for(int i = 0; i < inputs.getSlots(); ++i) {
                if (!inputs.getStackInSlot(i).isEmpty()) {
                    handlerItems.add(inputs.getStackInSlot(i).copy());
                }
            }

            Iterator<Ingredient> var12 = this.inputs.iterator();

            boolean found;
            do {
                if (!var12.hasNext()) {
                    return handlerItems.isEmpty() && tank.drainForced(this.inputFluid, IFluidHandler.FluidAction.SIMULATE).getAmount() == this.inputFluid.getAmount();
                }

                Ingredient ingredient = var12.next();
                found = false;
                ItemStack[] var7 = ingredient.getItems();
                int var8 = var7.length;

                for (ItemStack stack : var7) {
                    int i;
                    for (i = 0; i < handlerItems.size(); ++i) {
                        if (ItemStack.isSameItem(handlerItems.get(i), stack)) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        handlerItems.remove(i);
                        break;
                    }
                }
            } while(found);

            return false;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull Optional<ItemStack> getOutput() {
        return Optional.of(this.output.copy());
    }

    @Override
    public DeferredRecipe<ShapelessRecipe> getDeferredRecipe() {
        return IFERecipes.SHAPELESS;
    }
}
