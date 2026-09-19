package net.yxiao233.industrialforegoingextra.common.registry;

import com.buuz135.industrial.module.IModule;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import net.yxiao233.industrialforegoingextra.api.fluid.fluid.BaseFluidInstance;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryHelper;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryModule;
import net.yxiao233.industrialforegoingextra.common.fluid.LiquidDragonBreathInstance;
import net.yxiao233.industrialforegoingextra.common.fluid.LiquidSculkMatterInstance;

@RegistryModule
public class IFEFluids implements IModule {
    public static LiquidDragonBreathInstance LIQUID_DRAGON_BREATH;
    public static LiquidSculkMatterInstance LIQUID_SCULK_MATTER;
    public static BaseFluidInstance DRAGON_STAR_ESSENCE;
    public static BaseFluidInstance LIQUID_MALIC_ACID;
    @Override
    public void generateFeatures(DeferredRegistryHelper helper) {
        LIQUID_DRAGON_BREATH = RegistryHelper.animateFluid(helper,"liquid_dragon_breath",LiquidDragonBreathInstance::new);
        LIQUID_SCULK_MATTER = RegistryHelper.animateFluid(helper,"liquid_sculk_matter",LiquidSculkMatterInstance::new);
        DRAGON_STAR_ESSENCE = RegistryHelper.simpleFluid(helper,"dragon_star_essence");
        LIQUID_MALIC_ACID = RegistryHelper.simpleFluid(helper,"liquid_malic_acid");
    }
}
