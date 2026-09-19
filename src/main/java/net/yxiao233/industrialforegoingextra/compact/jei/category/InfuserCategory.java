package net.yxiao233.industrialforegoingextra.compact.jei.category;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.SlotsScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.common.config.machine.InfuserConfig;
import net.yxiao233.industrialforegoingextra.common.recipes.InfuserRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import net.yxiao233.industrialforegoingextra.compact.jei.IFERecipeType;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiCategory
public class InfuserCategory extends AbstractJEICategory<RecipeHolder<InfuserRecipe>> {
    public static final Component TITLE = Component.translatable("block.industrialforegoingextra.infuser");
    private final IDrawable bigTank;
    public InfuserCategory(IGuiHelper helper) {
        super(helper, IFERecipeType.INFUSER,TITLE, IFEBlocks.INFUSER.getBlock().asItem(), 160, 82);
        this.bigTank = helper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 177 + 3, 1 + 3, 12, 50);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getRecipe() {
        return (RecipeType<R>) IFERecipes.INFUSER.asType();
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull RecipeHolder<InfuserRecipe> recipe, @NotNull IFocusGroup iFocusGroup) {
        //Input
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 33).addIngredient(VanillaTypes.ITEM_STACK,recipe.value().input);
        //InputFluid
        if(recipe.value().inputFluid != null && !recipe.value().inputFluid.isEmpty()){
            builder.addSlot(RecipeIngredientRole.INPUT, 44 + 3, 12 + 3).setFluidRenderer(InfuserConfig.maxInputTankSize >= 1000 ? InfuserConfig.maxInputTankSize : 1000,false,12,50).setOverlay(bigTank,0,0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.value().inputFluid);
        }
        //Output
        if(!recipe.value().output.isEmpty()){
            ItemStack stack = recipe.value().output;
            stack.getItem().onCraftedBy(stack,null,null);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 16).addIngredient(VanillaTypes.ITEM_STACK,stack);
        }
    }

    @Override
    public void draw(@NotNull RecipeHolder<InfuserRecipe> recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //Background
        EnergyBarScreenAddon.drawBackground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0);
        //Input
        SlotsScreenAddon.drawAsset(guiGraphics,Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER,66,33,0,0,1,integer -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), integer -> ItemStack.EMPTY, true, integer -> new Color(DyeColor.LIGHT_BLUE.getFireworkColor()), integer -> true, 1);
        //Output
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, integer -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), integer -> ItemStack.EMPTY, true, integer -> new Color(DyeColor.ORANGE.getFireworkColor()), integer -> true, 1);
        //InputFluid
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, Objects.requireNonNull(DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL)), 44, 12);
        //ProgressBar
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 41 - 8);
        //EnergyBar
        int consumed = recipe.value().processingTime * InfuserConfig.powerPerTick;
        EnergyBarScreenAddon.drawForeground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0, consumed, (int) Math.max(50000, (double) consumed));
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, @NotNull RecipeHolder<InfuserRecipe> recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        super.getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);

        int consumed = recipe.value().processingTime * 60;
        addEnergyBarTooltip(tooltip,mouseX,mouseY,consumed,(int) Math.max(50000, (double) consumed));
    }

    @Override
    public void addRecipes(IRecipeRegistration registration) {
        List<RecipeHolder<InfuserRecipe>> infuserRecipes = new ArrayList<>();
        BuiltInRegistries.ITEM.stream().forEach(reg ->{
            if(reg instanceof MobBucketItem || reg.getDefaultInstance().is(Items.BUCKET)){
                return;
            }
            if(reg instanceof BucketItem bucketItem){
                FluidStack fluidStack = new FluidStack(bucketItem.content,1000);
                if(!fluidStack.isEmpty()){
                    infuserRecipes.add(new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingExtra.MODID,"infuser/" + BuiltInRegistries.ITEM.getKey(bucketItem).getNamespace() + "/" + BuiltInRegistries.ITEM.getKey(bucketItem).getPath()),new InfuserRecipe(Items.BUCKET.getDefaultInstance(),fluidStack,200,bucketItem.getDefaultInstance())));
                }
            }
        });
        registration.addRecipes(IFERecipeType.INFUSER,infuserRecipes);
    }
}
