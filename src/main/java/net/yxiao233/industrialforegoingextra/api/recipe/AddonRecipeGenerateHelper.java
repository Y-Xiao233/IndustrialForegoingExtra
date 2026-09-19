package net.yxiao233.industrialforegoingextra.api.recipe;

import com.buuz135.industrial.module.ModuleCore;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.api.addon.AddonMap;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AddonRecipeGenerateHelper {
    public static final AddonRecipeGenerateHelper INSTANCE = new AddonRecipeGenerateHelper();
    private final HashMap<Integer, Triple<Ingredient, Ingredient, FluidStack>> tierMaterialMap = new HashMap<>();
    private final HashMap<AddonMap, Pair<Ingredient, Ingredient>> materialMap = new HashMap<>();
    public AddonRecipeGenerateHelper defineTierMaterial(int targetTier, Ingredient ingredient1, Ingredient ingredient2, FluidStack fluidStack){
        tierMaterialMap.put(targetTier,Triple.of(ingredient1,ingredient2,fluidStack));
        return this;
    }
    public AddonRecipeGenerateHelper defineTyped(AddonMap map, Ingredient ingredient1, Ingredient ingredient2){
        materialMap.put(map,Pair.of(ingredient1,ingredient2));
        return this;
    }

    public void save(RecipeOutput recipeOutput){
        materialMap.forEach((map, ingredientIngredientPair) -> {
            map.forEach(item -> {
                if(item instanceof IFEAddonItem addonItem){
                    int tier = addonItem.getTier();
                    if(tier == 1){
                        Triple<Ingredient, Ingredient, FluidStack> triple = tierMaterialMap.get(tier);
                        IFERecipeBuilders.dissolutionChamberRecipe(addonItem.getDefaultInstance())
                                .inputs(
                                        tagValue(Tags.Items.DUSTS_REDSTONE),
                                        tagValue(Tags.Items.DUSTS_REDSTONE),
                                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                                        triple.getLeft(),
                                        triple.getMiddle(),
                                        ingredientIngredientPair.getLeft(),
                                        ingredientIngredientPair.getRight()
                                )
                                .inputFluid(triple.getRight())
                                .processingTime(200)
                                .outputFluid(FluidStack.EMPTY)
                                .save(recipeOutput);
                    }else if(tier > 1){
                        Triple<Ingredient, Ingredient, FluidStack> triple = tierMaterialMap.get(tier);
                        IFERecipeBuilders.dissolutionChamberRecipe(addonItem.getDefaultInstance())
                                .inputs(
                                        tagValue(Tags.Items.DUSTS_REDSTONE),
                                        tagValue(Tags.Items.DUSTS_REDSTONE),
                                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                                        itemValue(getLowAddon(map,tier).getDefaultInstance()),
                                        triple.getLeft(),
                                        triple.getMiddle(),
                                        ingredientIngredientPair.getLeft(),
                                        ingredientIngredientPair.getRight()
                                )
                                .inputFluid(triple.getRight())
                                .processingTime(200)
                                .outputFluid(FluidStack.EMPTY)
                                .save(recipeOutput);
                    }
                }
            });
        });
    }

    public Item getLowAddon(AddonMap map, int tier){
        DeferredHolder<Item, Item> addonItem = map.getAddonItem(tier - 1);
        if(addonItem != null){
            return addonItem.get();
        }else{
            if(map == IFEItems.SPEED_ADDONS){
                return ModuleCore.SPEED_ADDON_2.get();
            }else if(map == IFEItems.PROCESSING_ADDONS){
                return ModuleCore.PROCESSING_ADDON_2.get();
            }else if(map == IFEItems.EFFICIENCY_ADDONS){
                return ModuleCore.EFFICIENCY_ADDON_2.get();
            }
        }
        return null;
    }

    private static Ingredient tagValue(TagKey<Item> tagKey){
        return Ingredient.of(tagKey);
    }
    private static Ingredient itemValue(ItemStack itemStack){
        return Ingredient.of(itemStack);
    }
}
