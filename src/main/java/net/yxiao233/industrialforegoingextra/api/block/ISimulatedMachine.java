package net.yxiao233.industrialforegoingextra.api.block;

import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import net.minecraft.world.item.DyeColor;
import net.yxiao233.industrialforegoingextra.api.data.SimulatedCard;

public interface ISimulatedMachine<T extends IndustrialMachineTile<T>> {
    default IFactory<SidedInventoryComponent<T>> simulatedInventory(int x, int y, int position){
        return () -> (SidedInventoryComponent<T>) new SidedInventoryComponent<T>("simulated",x,y,1,position)
                .setColor(DyeColor.CYAN)
                .setInputFilter((stack, integer) -> stack.getItem() instanceof SimulatedCard);
    }

    SidedInventoryComponent<T> industrialForegoingExtra$getSimulatedInventory();
}
