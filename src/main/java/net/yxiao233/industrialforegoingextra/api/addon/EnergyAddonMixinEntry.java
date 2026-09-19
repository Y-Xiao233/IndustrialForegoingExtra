package net.yxiao233.industrialforegoingextra.api.addon;

import com.hrznstudio.titanium.block.tile.MachineTile;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import net.yxiao233.industrialforegoingextra.mixin.EnergyStorageAccessor;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;

public class EnergyAddonMixinEntry {
    private final MachineTile<?> tile;
    private final int baseCapacity;
    private EnergyAddonMixinEntry(MachineTile<?> tile, int baseCapacity){
        this.tile = tile;
        this.baseCapacity = baseCapacity;
    }

    public static EnergyAddonMixinEntry create(MachineTile<?> tile, int baseCapacity){
        return new EnergyAddonMixinEntry(tile,baseCapacity);
    }
    public void updateEnergyCapacity(){
        int tier = AugmentInventoryHelper.getAugmentTier(tile, IFEAddonType.ENERGY);
        EnergyStorageComponent<?> energyStorage = tile.getEnergyStorage();
        boolean isCreative = AugmentInventoryHelper.contains(tile,IFEAddonType.CREATIVE);
        int cap = Math.min((int) (baseCapacity * Math.pow(10,(tier + 2) / 2D)),Integer.MAX_VALUE);
        cap = tier > 0 ? cap : this.baseCapacity;
        cap = isCreative ? Integer.MAX_VALUE : cap;
        int old = energyStorage.getEnergyStored();
        if(energyStorage instanceof EnergyStorageAccessor accessor){
            accessor.setCapacity(cap);
            accessor.setMaxExtract(cap);
            accessor.setMaxReceive(cap);
            if(old > cap){
                energyStorage.setEnergyStored(cap);
            }
            tile.markForUpdate();
        }
    }
}
