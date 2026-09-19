package net.yxiao233.industrialforegoingextra.api.jei;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.api.recipe.IFluidGeneratorRecipeWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class IFEFluidFuelGeneratorCategory<T extends IFluidGeneratorRecipeWrapper> extends AbstractJEICategory<T> {
    private static final Component TITLE = Component.literal("null");
    private final IDrawable bigTank;
    public IFEFluidFuelGeneratorCategory(IGuiHelper helper, mezz.jei.api.recipe.RecipeType<T> type, Component title, Item icon) {
        super(helper,type,TITLE, icon, 95, 85);
        if(icon != null){
            this.title = Objects.requireNonNullElseGet(title, icon::getDescription);
        }
        this.bigTank = helper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 177 + 3, 1 + 3, 12, 50);
    }
    public abstract Class<?> getGeneratorConfigClass();

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull T recipe, @NotNull IFocusGroup iFocusGroup) {
        if(recipe.getInputFluid() != null && !recipe.getInputFluid().isEmpty()){
            int maxInputTankSize;
            try {
                Field field = getGeneratorConfigClass().getField("maxInputTankSize");
                maxInputTankSize = field.getInt(field.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            builder.addSlot(RecipeIngredientRole.INPUT, 16 + 3, 12 + 3).setFluidRenderer(Math.max(maxInputTankSize, 1000),false,12,50).setOverlay(bigTank,0,0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.getInputFluid());
        }
    }

    @Override
    public void draw(@NotNull T recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //Background
        EnergyBarScreenAddon.drawBackground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 72, 12, 0, 0);
        //InputFluid
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, Objects.requireNonNull(DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL)), 16, 12);
        //ProgressBar
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 42, 41 - 8);
        //EnergyBar
        int generated = recipe.getProcessingTime() * recipe.getPowerPerTick();
        EnergyBarScreenAddon.drawForeground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 72, 12, 0, 0, generated, (int) Math.max(50000, (double) generated));
        addEnergyBarTooltip(guiGraphics,getGeneratorConfigClass(),18,56,72,12,mouseX,mouseY);
    }
}