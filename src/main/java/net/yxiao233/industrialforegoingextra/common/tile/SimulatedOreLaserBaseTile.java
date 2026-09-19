package net.yxiao233.industrialforegoingextra.common.tile;

import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import com.buuz135.industrial.utils.ItemStackUtils;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.api.block.ISimulatedMachine;
import net.yxiao233.industrialforegoingextra.api.component.BigSidedInventoryComponent;
import net.yxiao233.industrialforegoingextra.api.data.ISimulatedCard;
import net.yxiao233.industrialforegoingextra.api.data.OreData;
import net.yxiao233.industrialforegoingextra.api.data.SimulatedCard;
import net.yxiao233.industrialforegoingextra.common.config.machine.SimulatedOreLaserBaseConfig;
import net.yxiao233.industrialforegoingextra.common.item.IFEFortuneAddonItem;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;
import net.yxiao233.industrialforegoingextra.util.SimulatedMachineEfficiencyHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatedOreLaserBaseTile extends IndustrialWorkingTile<SimulatedOreLaserBaseTile> implements ISimulatedMachine<SimulatedOreLaserBaseTile> {
    @Save
    private final BigSidedInventoryComponent<SimulatedOreLaserBaseTile> output;

    @Save
    private final SidedInventoryComponent<SimulatedOreLaserBaseTile> simulated;

    private final WorkAction ignore = new WorkAction(1.0f,0);

    public SimulatedOreLaserBaseTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.SIMULATED_ORE_LASER_BASE, SimulatedOreLaserBaseConfig.powerPerOperation, blockPos, blockState);

        this.addInventory(this.output = (BigSidedInventoryComponent<SimulatedOreLaserBaseTile>)(new BigSidedInventoryComponent<SimulatedOreLaserBaseTile>("output", 45, 22, 21, 0))
                .setColor(DyeColor.ORANGE)
                .setRange(7, 3)
                .setInputFilter((stack, integer) -> false)
                .setSlotLimit(SimulatedOreLaserBaseConfig.maxOutputSlotLimit)
        );

        this.addInventory(this.simulated = (SidedInventoryComponent<SimulatedOreLaserBaseTile>) this.simulatedInventory(88,80,1).create().setComponentHarness(this));
    }

    @Override
    public IndustrialWorkingTile<SimulatedOreLaserBaseTile>.WorkAction work() {
        if(level != null && this.getEnergyStorage().getEnergyStored() < SimulatedOreLaserBaseConfig.powerPerOperation){
            return ignore;
        }

        if(ItemStackUtils.isInventoryFull(this.output)){
            return ignore;
        }

        AtomicBoolean worked = new AtomicBoolean(false);
        ItemStack card = this.simulated.getStackInSlot(0);
        if(!card.isEmpty() && card.getItem() instanceof ISimulatedCard simulatedCard){
            if(simulatedCard.isDataEmpty(card)){
                return ignore;
            }
            if(card.has(IFEDataComponents.ORE_DATA)){
                OreData oreData = card.get(IFEDataComponents.ORE_DATA);
                if(oreData != null){
                    oreData.getTags().forEach(tag ->{
                        ItemStack ore = ItemStack.parseOptional(level.registryAccess(),tag.getCompound("item"));
                        double efficiency = tag.getDouble("efficiency");
                        ((SimulatedCard) card.getItem()).updateOreData(level,card,ore,1);
                        applyOutput(ore,efficiency);
                        worked.set(true);
                    });
                }
            }
        }
        return worked.get() ? new WorkAction(1.0f,SimulatedOreLaserBaseConfig.powerPerOperation) : ignore;
    }

    private void applyOutput(ItemStack ore, double efficiency){
        int tier = AugmentInventoryHelper.getAugmentTier(this, IFEAddonType.FORTUNE) + 1;
        int multiple = SimulatedMachineEfficiencyHelper.getMultiple(efficiency,tier);

        int count = ore.copy().getCount();
        ItemHandlerHelper.insertItem(this.output, ore.copyWithCount(multiple * count), false);
    }

    @Override
    public boolean canAcceptAugment(ItemStack augment) {
        if(augment.getItem() instanceof IFEFortuneAddonItem){
            return AugmentInventoryHelper.canAccept(this,augment);
        }
        return super.canAcceptAugment(augment);
    }

    @Override
    protected @NotNull EnergyStorageComponent<SimulatedOreLaserBaseTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(SimulatedOreLaserBaseConfig.maxStoredPower, 10, 20);
    }

    @Override
    public int getMaxProgress() {
        return SimulatedOreLaserBaseConfig.maxProgress;
    }

    @NotNull
    @Override
    public SimulatedOreLaserBaseTile getSelf() {
        return this;
    }

    @Override
    public SidedInventoryComponent<SimulatedOreLaserBaseTile> industrialForegoingExtra$getSimulatedInventory() {
        return null;
    }
}
