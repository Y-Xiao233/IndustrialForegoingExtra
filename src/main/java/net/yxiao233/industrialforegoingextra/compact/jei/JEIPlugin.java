package net.yxiao233.industrialforegoingextra.compact.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.util.JeiHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private final List<AbstractJEICategory<?>> categories = new ArrayList<>();
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return IndustrialForegoingExtra.makeId("jei_plugin");
    }

    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
        JeiHelper.hideItem(jeiRuntime,IFEItems.AIR);
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        JeiHelper.loadCategoriesFromAnnotation(registration,categories);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        JeiHelper.registerRecipeCatalysts(registration,categories);
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        JeiHelper.registerRecipes(registration,categories);
    }
}
