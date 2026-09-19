package net.yxiao233.industrialforegoingextra.api.item;

import com.buuz135.industrial.item.addon.AddonItem;
import com.hrznstudio.titanium.api.ISpecialCreativeTabItem;
import com.hrznstudio.titanium.item.AugmentWrapper;
import com.hrznstudio.titanium.item.BasicItem;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.addon.IAddonType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class IFEAddonItem extends AddonItem implements ISpecialCreativeTabItem {
    private final int tier;
    private final IAddonType type;
    public IFEAddonItem(IAddonType type, int tier) {
        super(type.getTypeId() + "_addon_tier" + (tier > 0 ? "_" + tier : ""), IndustrialForegoingExtra.TAB, new Properties().stacksTo(16));
        this.type = type;
        this.tier = tier;
    }

    @Override
    public @NotNull String getDescriptionId() {
        String var10000 = Component.translatable("item.industrialforegoing.addon").getString();
        return var10000 + Component.translatable("item.industrialforegoingextra." + this.type.getTypeId()).getString() + (tier > 0 ? Component.translatable("item.industrialforegoing.tier").getString() + this.getTier() + " " : " ");
    }

    @Override
    public void addToTab(BuildCreativeModeTabContentsEvent event) {
        ItemStack stack = new ItemStack(this);
        AugmentWrapper.setType(stack, type.getAugmentType(), this.getFormula());
        event.accept(stack);
    }

    @Override
    public void verifyComponentsAfterLoad(@NotNull ItemStack stack) {
        super.verifyComponentsAfterLoad(stack);
        AugmentWrapper.setType(stack, this.getType().getAugmentType(), this.getFormula());
    }

    @Override
    public boolean hasTooltipDetails(@Nullable BasicItem.Key key) {
        return key == null;
    }

    protected abstract float getFormula();

    public int getTier(){
        return tier;
    }
    public IAddonType getType(){
        return type;
    }

    @Override
    public void registerRecipe(RecipeOutput recipeOutput) {

    }
}
