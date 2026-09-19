package net.yxiao233.industrialforegoingextra.common.registry;

import com.buuz135.industrial.module.IModule;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryHelper;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryModule;
import net.yxiao233.industrialforegoingextra.common.recipes.*;

@RegistryModule
public class IFERecipes implements IModule {
    public static DeferredRecipe<InfuserRecipe> INFUSER;
    public static DeferredRecipe<ArcaneDragonEggForgingRecipe> ARCANE_DRAGON_EGG_FORGING;
    public static DeferredRecipe<ShapedRecipe> SHAPED;
    public static DeferredRecipe<ShapelessRecipe> SHAPELESS;
    @Override
    public void generateFeatures(DeferredRegistryHelper helper) {
        INFUSER = RegistryHelper.codecRecipe(helper,"infuser", InfuserRecipe.class);
        ARCANE_DRAGON_EGG_FORGING = RegistryHelper.codecRecipe(helper,"arcane_dragon_egg_forging", ArcaneDragonEggForgingRecipe.class);
        SHAPED = RegistryHelper.codecRecipe(helper,"shaped", ShapedRecipe.class);
        SHAPELESS = RegistryHelper.codecRecipe(helper,"shapeless", ShapelessRecipe.class);
    }
}
