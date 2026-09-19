package net.yxiao233.industrialforegoingextra.common.tile;

import com.buuz135.industrial.config.machine.core.DissolutionChamberConfig;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.DissolutionChamberRecipe;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.component.bundle.LockableInventoryBundle;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.api.tile.MultiIndustrialProcessingTile;
import net.yxiao233.industrialforegoingextra.common.config.machine.BigDissolutionChamberConfig;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;
import net.yxiao233.industrialforegoingextra.util.InventoryComponentHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class BigDissolutionChamberTile extends MultiIndustrialProcessingTile<BigDissolutionChamberTile> {
    private final int maxProgress;
    private final int powerPerTick;
    private final int defaultMaxThread;
    @Save
    private LockableInventoryBundle<BigDissolutionChamberTile> input;
    @Save
    private FluidTankComponent<BigDissolutionChamberTile> inputFluid;
    @Save
    private InventoryComponent<BigDissolutionChamberTile> output;
    @Save
    private FluidTankComponent<BigDissolutionChamberTile> outputFluid;
    private DissolutionChamberRecipe currentRecipe;
    public BigDissolutionChamberTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.BIG_DISSOLUTION_CHAMBER, 102, 41, blockPos, blockState);
        int slotSpacing = 22;
        this.addBundle(this.input = new LockableInventoryBundle<>(this,(new InventoryComponent<BigDissolutionChamberTile>("input",34,19,8))
                .setSlotPosition(BigDissolutionChamberTile::getSlotPos)
                .setOutputFilter((stack, integer) -> false)
                .setComponentHarness(this)
                .setInputFilter((stack, integer) -> !this.canIncrease())
                .setOnSlotChanged((stack, integer) -> this.checkForRecipe()),100, 64, false));
        this.addTank(this.inputFluid = (new FluidTankComponent<BigDissolutionChamberTile>("input_fluid", BigDissolutionChamberConfig.maxInputTankSize, 33 + slotSpacing, 18 + slotSpacing))
                .setTankType(FluidTankComponent.Type.SMALL)
                .setComponentHarness(this)
                .setOnContentChange(this::checkForRecipe));
        this.addInventory(this.output = (new InventoryComponent<BigDissolutionChamberTile>("output", 129, 22, 3))
                .setRange(1, 3)
                .setInputFilter((stack, integer) -> false)
                .setComponentHarness(this));
        this.addTank(this.outputFluid = (new FluidTankComponent<BigDissolutionChamberTile>("output_fluid", BigDissolutionChamberConfig.maxOutputTankSize, 149, 20))
                .setComponentHarness(this)
                .setTankAction(FluidTankComponent.Action.DRAIN));

        this.maxProgress = BigDissolutionChamberConfig.maxProgress;
        this.powerPerTick = BigDissolutionChamberConfig.powerPerTick;
        this.defaultMaxThread = BigDissolutionChamberConfig.maxThread;
    }
    public static Pair<Integer, Integer> getSlotPos(int slot) {
        int slotSpacing = 22;
        int offset = 2;
        return switch (slot) {
            case 1 -> Pair.of(slotSpacing, -offset);
            case 2 -> Pair.of(slotSpacing * 2, 0);
            case 3 -> Pair.of(-offset, slotSpacing);
            case 4 -> Pair.of(slotSpacing * 2 + offset, slotSpacing);
            case 5 -> Pair.of(0, slotSpacing * 2);
            case 6 -> Pair.of(slotSpacing, slotSpacing * 2 + offset);
            case 7 -> Pair.of(slotSpacing * 2, slotSpacing * 2);
            default -> Pair.of(0, 0);
        };
    }

    @Override
    public boolean isOffsetCapabilityDisabled(BlockCapability<?, @Nullable Direction> capability, @Nullable Direction side, BlockPos offset) {
//        if (capability == Capabilities.EnergyStorage.BLOCK) {
//            return notEnergyPort(side, offset);
//        } else if (capability == Capabilities.ItemHandler.BLOCK || capability == Capabilities.FluidHandler.BLOCK) {
//            return notItemFluidPort(side, offset);
//        }
//        return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getOffsetCapabilityIfEnabled(BlockCapability<T, @Nullable Direction> capability, @Nullable Direction side, BlockPos offset) {
//        if (capability == Capabilities.EnergyStorage.BLOCK) {
//            return (T) this.getEnergyStorage();
//        } else if (capability == Capabilities.ItemHandler.BLOCK) {
//            return (T) (isLeftPort(offset) ? input.getInventory() : output);
//        } else if (capability == Capabilities.FluidHandler.BLOCK) {
//            return (T) (isLeftPort(offset) ? inputFluid : outputFluid);
//        }
//        return super.getOffsetCapabilityIfEnabled(capability, side, offset);

        if (capability == Capabilities.EnergyStorage.BLOCK) {
            return (T) this.getEnergyStorage();
        } else if (capability == Capabilities.ItemHandler.BLOCK) {
            return (T) this.getItemHandler(side);
        } else if (capability == Capabilities.FluidHandler.BLOCK) {
            return (T) this.getFluidHandler(side);
        }
        return super.getOffsetCapabilityIfEnabled(capability, side, offset);
    }

    private @NotNull Direction getFacing() {
        BlockState state = getBlockState();
        return state.hasProperty(RotatableBlock.FACING_HORIZONTAL) ? state.getValue(RotatableBlock.FACING_HORIZONTAL) : Direction.NORTH;
    }

    private BlockPos portOffset(Direction side) {
        return new BlockPos(side.getStepX(), 0, side.getStepZ());
    }

    private boolean isLeftPort(BlockPos offset) {
        return offset.equals(portOffset(getFacing().getClockWise()));
    }

    private boolean notEnergyPort(@Nullable Direction side, BlockPos offset) {
        Direction back = getFacing().getOpposite();
        if (offset.equals(portOffset(back))) {
            return side != back;
        }
        return true;
    }

    private boolean notItemFluidPort(@Nullable Direction side, BlockPos offset) {
        Direction left = getFacing().getClockWise();
        if (offset.equals(portOffset(left))) {
            return side != left;
        }
        Direction right = left.getOpposite();
        if (offset.equals(portOffset(right))) {
            return side != right;
        }
        return true;
    }

    @Override
    public boolean canIncrease() {
        return this.currentRecipe != null && ItemHandlerHelper.insertItem(this.output, this.currentRecipe.output.orElse(ItemStack.EMPTY).copy(), true).isEmpty() && (this.currentRecipe.outputFluid.isEmpty() || this.outputFluid.fillForced(((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).copy(), IFluidHandler.FluidAction.SIMULATE) == ((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).getAmount());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void checkForRecipe() {
        if (this.isServer()) {
            if (this.currentRecipe != null && this.currentRecipe.matches(this.input.getInventory(), this.inputFluid)) {
                return;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, (RecipeType<DissolutionChamberRecipe>) ModuleCore.DISSOLUTION_TYPE.get()).stream().filter(recipe -> recipe.matches(this.input.getInventory(), this.inputFluid)).findFirst().orElse(null);
        }
    }

    @Override
    public boolean canAcceptAugment(ItemStack augment) {
        if(augment.getItem() instanceof IFEAddonItem item && item.getType().equals(IFEAddonType.THREAD)){
            return AugmentInventoryHelper.canAccept(this.getAugmentInventory(),augment);
        }
        return super.canAcceptAugment(augment);
    }

    @Override
    public Runnable onFinish() {
        return () -> {
            if (this.currentRecipe != null) {
                DissolutionChamberRecipe dissolutionChamberRecipe = this.currentRecipe;


                int thread = Math.min(getCurThread(dissolutionChamberRecipe),getMaxOutputThread());

                Optional<FluidStack> optionalInputFluid = Arrays.stream(dissolutionChamberRecipe.inputFluid.getFluids()).findFirst();

                if(optionalInputFluid.isPresent()){
                    this.inputFluid.drainForced(new FluidStack(optionalInputFluid.get().getFluid(),optionalInputFluid.get().getAmount() * thread), IFluidHandler.FluidAction.EXECUTE);

                    for(int i = 0; i < this.input.getInventory().getSlots(); ++i) {
                        this.input.getInventory().getStackInSlot(i).shrink(thread);
                    }

                    dissolutionChamberRecipe.outputFluid.ifPresent(stack -> this.outputFluid.fillForced(new FluidStack(stack.getFluid(), stack.getAmount() * thread), IFluidHandler.FluidAction.EXECUTE));

                    if(dissolutionChamberRecipe.output.isPresent()){
                        ItemStack outputStack = dissolutionChamberRecipe.output.get().copy();
                        outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                        ItemHandlerHelper.insertItem(this.output, new ItemStack(outputStack.getItem(),outputStack.getCount() * thread), false);
                    }
                    this.checkForRecipe();
                }
            }

        };
    }

    public int getCurThread(DissolutionChamberRecipe recipe){
        int thread = getMaxOutputThread();
        for (int i = 0; i < this.input.getInventory().getSlots(); i++) {
            if(!this.input.getInventory().getStackInSlot(i).isEmpty()){
                thread = Math.min(this.input.getInventory().getStackInSlot(i).getCount(),thread);
            }
        }
        if(recipe.output.isPresent()){
            int max = InventoryComponentHelper.canInsetMaxCount(this.output,new ItemStack(recipe.output.get().getItem(),thread));
            thread = Math.min(thread,max);
        }

        Optional<FluidStack> optionalInputFluid = Arrays.stream(recipe.inputFluid.getFluids()).findFirst();
        if(optionalInputFluid.isPresent()){
            int fluidAmount = this.inputFluid.getFluid().getAmount();
            int recipeNeededFluid = optionalInputFluid.get().getAmount();
            thread = recipeNeededFluid * thread <= fluidAmount ? thread : (int) fluidAmount / recipeNeededFluid;
        }

        if(recipe.outputFluid.isPresent()){
            int remainingOutputCapacity = this.outputFluid.getCapacity() - this.outputFluid.getFluidAmount();
            int outputFluidAmount = recipe.outputFluid.get().copy().getAmount();
            thread = outputFluidAmount * thread <= remainingOutputCapacity ? thread : remainingOutputCapacity / outputFluidAmount;
        }

        return thread;
    }

    public int getMaxOutputThread(){
        int tier1 = AugmentInventoryHelper.getAugmentTier(this.getAugmentInventory(), IFEAddonType.THREAD);
        int tier2 = AugmentInventoryHelper.getAugmentTier(this.getAugmentInventory(), IFEAddonType.PROCESSING);
        return (tier1 * 4 + defaultMaxThread) * tier2 <= 0 ? 1 : tier2;
    }

    protected @NotNull EnergyStorageComponent<BigDissolutionChamberTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(DissolutionChamberConfig.maxStoredPower, 10, 20);
    }

    @Override
    protected int getTickPower() {
        return this.powerPerTick;
    }

    @Override
    public int getMaxProgress() {
        if(this.getAugmentInventory() == null){
            return this.currentRecipe != null ? this.currentRecipe.processingTime / 2 : this.maxProgress;
        }
        if(AugmentInventoryHelper.contains(this.getAugmentInventory(), IFEAddonType.CREATIVE)){
            return 1;
        }
        return this.currentRecipe != null ? this.currentRecipe.processingTime / 2 : this.maxProgress;
    }

    @Override
    public void loadSettings(Player player, CompoundTag tag) {
        if (tag.contains("BDC_locked")) {
            this.input.setLocked(tag.getBoolean("BDC_locked"));
        }

        String psFilter;
        if (tag.contains("BDC_filter")) {
            for(Iterator<String> var3 = tag.getCompound("BDC_filter").getAllKeys().iterator(); var3.hasNext(); this.input.getFilter()[Integer.parseInt(psFilter)] = ItemStack.parseOptional(this.level.registryAccess(), tag.getCompound("BDC_filter").getCompound(psFilter))) {
                psFilter = var3.next();
            }
        }

        super.loadSettings(player, tag);
    }

    @Override
    public void saveSettings(Player player, CompoundTag tag) {
        tag.putBoolean("BDC_locked", this.input.isLocked());
        CompoundTag filterTag = new CompoundTag();

        for(int i = 0; i < this.input.getFilter().length; ++i) {
            filterTag.put("" + i, this.input.getFilter()[i].saveOptional(this.level.registryAccess()));
        }

        tag.put("BDC_filter", filterTag);
        super.saveSettings(player, tag);
    }

    @Override
    public @NotNull BigDissolutionChamberTile getSelf() {
        return this;
    }
}
