package net.yxiao233.industrialforegoingextra.compact.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeRegistration;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.api.jei.IFEFluidFuelGeneratorCategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.common.config.machine.DragonGeneratorConfig;
import net.yxiao233.industrialforegoingextra.api.recipe.FluidGeneratorRecipeWrapper;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.tile.DragonGeneratorTile;
import net.yxiao233.industrialforegoingextra.compact.jei.IFERecipeType;

import java.util.ArrayList;
import java.util.List;

@JeiCategory
public class DragonGeneratorCategory extends IFEFluidFuelGeneratorCategory<DragonGeneratorCategory.DragonGeneratorRecipeWrapper> {

    public DragonGeneratorCategory(IGuiHelper helper) {
        super(helper, IFERecipeType.DRAGON_GENERATOR,null, IFEBlocks.DRAGON_GENERATOR.asItem());
    }

    @Override
    public Class<?> getGeneratorConfigClass() {
        return DragonGeneratorConfig.class;
    }

    @Override
    public void addRecipes(IRecipeRegistration registration) {
        List<DragonGeneratorRecipeWrapper> recipes = new ArrayList<>();
        recipes.add(new DragonGeneratorCategory.DragonGeneratorRecipeWrapper(2400,4000, DragonGeneratorTile.FLUID_FUEL));
        registration.addRecipes(IFERecipeType.DRAGON_GENERATOR,recipes);
    }

    public static class DragonGeneratorRecipeWrapper extends FluidGeneratorRecipeWrapper {
        public DragonGeneratorRecipeWrapper(int processingTime, int powerPerTick, FluidStack inputFluid) {
            super(processingTime, powerPerTick, inputFluid);
        }
    }
}
