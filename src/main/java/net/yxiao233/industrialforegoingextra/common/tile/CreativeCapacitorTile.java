package net.yxiao233.industrialforegoingextra.common.tile;

import com.buuz135.industrial.block.tile.IndustrialGeneratorTile;
import com.buuz135.industrial.item.infinity.InfinityEnergyStorage;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import org.jetbrains.annotations.NotNull;

public class CreativeCapacitorTile extends IndustrialGeneratorTile<CreativeCapacitorTile> {
    @Save
    private SidedInventoryComponent<CreativeCapacitorTile> chargingSlot;
    private final int MAX_INT_VALUE = Integer.MAX_VALUE;
    public CreativeCapacitorTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.CREATIVE_CAPACITOR, blockPos, blockState);
        this.addInventory(this.chargingSlot = (SidedInventoryComponent<CreativeCapacitorTile>)(new SidedInventoryComponent<CreativeCapacitorTile>("charging", 80, 40, 1, 0))
                .setColor(DyeColor.BLUE)
                .setSlotLimit(1)
                .setInputFilter((stack, integer) -> {
                    return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
                })
        );
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, CreativeCapacitorTile blockEntity) {
        super.serverTick(level,pos,state,blockEntity);
        if (!this.chargingSlot.getStackInSlot(0).isEmpty()) {
            IEnergyStorage energyStorage = this.chargingSlot.getStackInSlot(0).getCapability(Capabilities.EnergyStorage.ITEM);
            if(energyStorage instanceof InfinityEnergyStorage<?> infinityEnergyStorage){
                long added = Math.min(this.getEnergyStorage().getEnergyStored(),Long.MAX_VALUE - infinityEnergyStorage.getLongEnergyStored());
                infinityEnergyStorage.setEnergyStored(infinityEnergyStorage.getLongEnergyStored() + added);
                this.getEnergyStorage().setEnergyStored((int) (this.getEnergyStorage().getEnergyStored() - added));
                this.markForUpdate();
            }else if(energyStorage.canReceive() && energyStorage.getEnergyStored() <= energyStorage.getMaxEnergyStored()){
                int added = energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();
                energyStorage.receiveEnergy(added,false);
            }
        }
    }

    @Override
    public int consumeFuel() {
        return MAX_INT_VALUE;
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public int getEnergyProducedEveryTick() {
        if(this.getEnergyStorage().getEnergyStored() == MAX_INT_VALUE){
            return MAX_INT_VALUE;
        }else {
            return this.getEnergyStorage().getMaxEnergyStored() - this.getEnergyStorage().getEnergyStored();
        }
    }

    @Override
    public ProgressBarComponent<CreativeCapacitorTile> getProgressBar() {
        return new ProgressBarComponent<CreativeCapacitorTile>(30,20,0,15)
                .setComponentHarness(this)
                .setBarDirection(ProgressBarComponent.BarDirection.VERTICAL_UP)
                .setColor(DyeColor.CYAN);
    }

    @Override
    public int getEnergyCapacity() {
        return MAX_INT_VALUE;
    }

    @Override
    public int getExtractingEnergy() {
        return MAX_INT_VALUE;
    }

    @NotNull
    @Override
    public CreativeCapacitorTile getSelf() {
        return this;
    }
}
