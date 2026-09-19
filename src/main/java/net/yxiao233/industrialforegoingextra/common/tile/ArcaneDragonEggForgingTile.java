package net.yxiao233.industrialforegoingextra.common.tile;

import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.yxiao233.industrialforegoingextra.common.config.machine.ArcaneDragonForgingConfig;
import net.yxiao233.industrialforegoingextra.common.recipes.ArcaneDragonEggForgingRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import org.jetbrains.annotations.NotNull;

public class ArcaneDragonEggForgingTile extends IndustrialProcessingTile<ArcaneDragonEggForgingTile> {
    private final int maxProgress;
    @Save
    private SidedInventoryComponent<ArcaneDragonEggForgingTile> input;
    @Save
    private SidedFluidTankComponent<ArcaneDragonEggForgingTile> inputFluid1;
    @Save
    private SidedFluidTankComponent<ArcaneDragonEggForgingTile> inputFluid2;
    @Save
    private SidedInventoryComponent<ArcaneDragonEggForgingTile> output;
    @Save
    private SidedFluidTankComponent<ArcaneDragonEggForgingTile> outputFluid;
    private ArcaneDragonEggForgingRecipe currentRecipe;
    public ArcaneDragonEggForgingTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.ARCANE_DRAGON_EGG_FORGING, 102, 41, blockPos, blockState);
        int slotSpacing = 22;

        this.addInventory(this.input = (SidedInventoryComponent<ArcaneDragonEggForgingTile>) new SidedInventoryComponent<ArcaneDragonEggForgingTile>("input",54+slotSpacing,18+slotSpacing,1,0)
                .setColor(DyeColor.LIGHT_BLUE)
                .setRange(1,1)
                .setOutputFilter((itemStack, integer) -> false)
                .setInputFilter((itemStack, integer) -> !canIncrease())
                .setOnSlotChanged((itemStack, integer) -> checkForRecipe())
                .setComponentHarness(this));

        this.addTank(this.inputFluid1 = (SidedFluidTankComponent<ArcaneDragonEggForgingTile>) new SidedFluidTankComponent<ArcaneDragonEggForgingTile>("input_fluid1", ArcaneDragonForgingConfig.maxInputTankSize,32,19,1)
                .setColor(DyeColor.LIME)
                .setTankType(FluidTankComponent.Type.NORMAL)
                .setComponentHarness(this)
                .setOnContentChange(this::checkForRecipe));

        this.addTank(this.inputFluid2 = (SidedFluidTankComponent<ArcaneDragonEggForgingTile>) new SidedFluidTankComponent<ArcaneDragonEggForgingTile>("input_fluid2", ArcaneDragonForgingConfig.maxInputTankSize,52,19,2)
                .setColor(DyeColor.ORANGE)
                .setTankType(FluidTankComponent.Type.NORMAL)
                .setComponentHarness(this)
                .setOnContentChange(this::checkForRecipe));

        this.addInventory(this.output = (SidedInventoryComponent<ArcaneDragonEggForgingTile>) new SidedInventoryComponent<ArcaneDragonEggForgingTile>("output", 129, 22, 3, 3)
                .setColor(DyeColor.MAGENTA)
                .setRange(1, 3)
                .setInputFilter((stack, integer) -> false)
                .setComponentHarness(this));

        this.addTank(this.outputFluid = (SidedFluidTankComponent<ArcaneDragonEggForgingTile>) new SidedFluidTankComponent<ArcaneDragonEggForgingTile>("output_fluid", ArcaneDragonForgingConfig.maxOutputTankSize, 149, 20, 4)
                .setColor(DyeColor.RED)
                .setComponentHarness(this)
                .setTankAction(FluidTankComponent.Action.DRAIN));

        this.maxProgress = 100;
    }
    private void checkForRecipe(){
        if(isServer()){
            if(currentRecipe != null && currentRecipe.matches(input, inputFluid1,inputFluid2)){
                return;
            }

            currentRecipe = RecipeUtil.getRecipes(this.level, IFERecipes.ARCANE_DRAGON_EGG_FORGING.asType()).stream().filter(recipe -> recipe.matches(input,inputFluid1,inputFluid2)).findFirst().orElse(null);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        checkForRecipe();
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
        checkForRecipe();
    }
    @Override
    public boolean canIncrease() {
        if(currentRecipe != null){
            boolean hasCurrentItem = currentRecipe.output.orElse(ItemStack.EMPTY).isEmpty() || ItemHandlerHelper.insertItem(output, currentRecipe.output.orElse(ItemStack.EMPTY).copy(),true).isEmpty();
            boolean hasEnoughCount = input.getStackInSlot(0).getCount() >= currentRecipe.input.getCount();
            boolean canFillFluid = (this.currentRecipe.outputFluid.isEmpty() || this.outputFluid.fillForced(this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY).copy(), IFluidHandler.FluidAction.SIMULATE) == this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY).getAmount());

            return hasCurrentItem && hasEnoughCount && canFillFluid;
        }else {
            return false;
        }
    }

    @Override
    public Runnable onFinish() {
        return () -> {
            if (this.currentRecipe != null) {
                ArcaneDragonEggForgingRecipe arcaneDragonEggForgingRecipe = this.currentRecipe;

                this.inputFluid1.drainForced(arcaneDragonEggForgingRecipe.inputFluid1, IFluidHandler.FluidAction.EXECUTE);
                this.inputFluid2.drainForced(arcaneDragonEggForgingRecipe.inputFluid2, IFluidHandler.FluidAction.EXECUTE);

                input.getStackInSlot(0).shrink(arcaneDragonEggForgingRecipe.input.getCount());

                if (arcaneDragonEggForgingRecipe.outputFluid.isPresent() && !arcaneDragonEggForgingRecipe.outputFluid.get().isEmpty()) {
                    this.outputFluid.fillForced(arcaneDragonEggForgingRecipe.outputFluid.get().copy(), IFluidHandler.FluidAction.EXECUTE);
                }
                if(arcaneDragonEggForgingRecipe.output.isPresent()){
                    ItemStack outputStack = arcaneDragonEggForgingRecipe.output.get().copy();
                    outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                    ItemHandlerHelper.insertItem(this.output, outputStack, false);
                }
                this.checkForRecipe();
            }

        };
    }

    @NotNull
    @Override
    protected EnergyStorageComponent<ArcaneDragonEggForgingTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(ArcaneDragonForgingConfig.maxStoredPower,10,20);
    }
    @Override
    protected int getTickPower() {
        return ArcaneDragonForgingConfig.powerPerTick;
    }

    @Override
    public int getMaxProgress() {
        return currentRecipe != null ? currentRecipe.processingTime : maxProgress;
    }
    @NotNull
    @Override
    public ArcaneDragonEggForgingTile getSelf() {
        return this;
    }
}

