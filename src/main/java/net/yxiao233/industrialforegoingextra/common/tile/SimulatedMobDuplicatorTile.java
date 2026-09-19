package net.yxiao233.industrialforegoingextra.common.tile;

import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import com.buuz135.industrial.capability.tile.BigEnergyHandler;
import com.buuz135.industrial.item.MobImprisonmentToolItem;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.yxiao233.industrialforegoingextra.api.block.ISimulatedMachine;
import net.yxiao233.industrialforegoingextra.api.data.ISimulatedCard;
import net.yxiao233.industrialforegoingextra.api.data.SimulatedCard;
import net.yxiao233.industrialforegoingextra.common.config.machine.SimulatedMobDuplicatorConfig;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class SimulatedMobDuplicatorTile extends IndustrialWorkingTile<SimulatedMobDuplicatorTile> implements ISimulatedMachine<SimulatedMobDuplicatorTile> {
    private final int maxProgress;
    private final int powerPerOperation;
    @Save
    private SidedFluidTankComponent<SimulatedMobDuplicatorTile> tank;
    @Save
    private SidedInventoryComponent<SimulatedMobDuplicatorTile> input;
    @Save
    private SidedInventoryComponent<SimulatedMobDuplicatorTile> simulated;
    private final WorkAction ignore = new WorkAction(1.0f,0);
    @SuppressWarnings("unchecked")
    public SimulatedMobDuplicatorTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.SIMULATED_MOB_DUPLICATOR, SimulatedMobDuplicatorConfig.powerPerOperation, blockPos, blockState);
        this.addTank(this.tank =  (SidedFluidTankComponent<SimulatedMobDuplicatorTile>) (new SidedFluidTankComponent<>("essence", SimulatedMobDuplicatorConfig.tankSize, 43, 20, 0)).setColor(DyeColor.LIME).setTankAction(FluidTankComponent.Action.FILL).setComponentHarness(this).setValidator((fluidStack) -> fluidStack.is(IndustrialTags.Fluids.EXPERIENCE)));
        this.addInventory(this.input = (SidedInventoryComponent<SimulatedMobDuplicatorTile>) new SidedInventoryComponent<SimulatedMobDuplicatorTile>("mob_imprisonment_tool",64,22,9,1)
                .setColor(DyeColor.ORANGE)
                .setRange(3,3)
                .setInputFilter((stack, integer) -> stack.getItem() instanceof MobImprisonmentToolItem)
                .setComponentHarness(this)
        );

        this.addInventory(this.simulated = (SidedInventoryComponent<SimulatedMobDuplicatorTile>) simulatedInventory(128,40,2).create().setComponentHarness(this));
        this.maxProgress = SimulatedMobDuplicatorConfig.maxProgress;
        this.powerPerOperation = SimulatedMobDuplicatorConfig.powerPerOperation;
    }

    @Override
    public IndustrialWorkingTile<SimulatedMobDuplicatorTile>.WorkAction work() {
        if(this.getEnergyStorage().getEnergyStored() < this.powerPerOperation){
            return ignore;
        }

        if(this.tank.getFluid().isEmpty()){
            return ignore;
        }

        boolean worked = false;
        for (int i = 0; i < this.input.getSlots(); i++) {
            ItemStack stack = this.input.getStackInSlot(i);
            if(stack.isEmpty()){
                continue;
            }
            if(stack.getItem() instanceof MobImprisonmentToolItem toolItem){
                Entity entity = toolItem.getEntityFromStack(stack, this.level, true, true);
                if(!(entity instanceof LivingEntity livingEntity)){
                    continue;
                }
                ItemStack card = this.simulated.getStackInSlot(0);
                if(card.isEmpty() || !(card.getItem() instanceof ISimulatedCard)){
                    return ignore;
                }else{
                    int essenceNeeded = (int)(livingEntity.getHealth() * (float) SimulatedMobDuplicatorConfig.essenceNeeded);
                    SimulatedCard simulatedCard = (SimulatedCard) card.getItem();
                    if(simulatedCard.isDataEmpty(card)){
                        simulatedCard.createMobData(card,livingEntity);
                        this.tank.drainForced(essenceNeeded, IFluidHandler.FluidAction.EXECUTE);
                        worked = true;
                    }else if(simulatedCard.is(card,"mob")){
                        simulatedCard.updateMobData(card,livingEntity,1);
                        this.tank.drainForced(essenceNeeded, IFluidHandler.FluidAction.EXECUTE);
                        worked = true;
                    }
                }
            }
        }
        return worked ? new WorkAction(1.0f,this.powerPerOperation) : ignore;
    }


    @NotNull
    @Override
    public SimulatedMobDuplicatorTile getSelf() {
        return this;
    }

    @Override
    public int getMaxProgress() {
        return this.maxProgress;
    }

    @Nonnull
    protected EnergyStorageComponent<SimulatedMobDuplicatorTile> createEnergyStorage() {
        return new BigEnergyHandler<>(SimulatedMobDuplicatorConfig.maxStoredPower, 10, 20) {
            public void sync() {
                SimulatedMobDuplicatorTile.this.syncObject(SimulatedMobDuplicatorTile.this.getEnergyStorage());
            }
        };
    }

    @Override
    public SidedInventoryComponent<SimulatedMobDuplicatorTile> industrialForegoingExtra$getSimulatedInventory() {
        return simulated;
    }
}
