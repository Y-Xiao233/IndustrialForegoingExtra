package net.yxiao233.industrialforegoingextra.datagen;

import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.api.IRecipeProvider;
import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class IFERecipeProvider extends VanillaRecipeProvider {
    public IFERecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput,completableFuture);
    }
    public static final String modId = IndustrialForegoingExtra.MODID;

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        BuiltInRegistries.ITEM.stream().forEach(reg ->{
            //Upgrades
            if(reg instanceof IFEAddonItem addonItem){
                addonItem.registerRecipe(recipeOutput);
            }
        });

        BuiltInRegistries.BLOCK.stream().forEach((reg) -> {
            if(reg instanceof BasicBlock){
                String s = reg.toString();
                String nameSpace = s.substring(6,s.indexOf(':'));
                if(nameSpace.equals("industrialforegoingextra")){
                    ((IRecipeProvider) reg).registerRecipe(recipeOutput);
                }
            }
        });


        TitaniumShapelessRecipeBuilder.shapelessRecipe(IFEItems.LASER_LENS_SCULK.get())
                .requires(Ingredient.of(Arrays.stream(ModuleCore.LASER_LENS).map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList()).stream()))
                .requires(Items.SCULK,4)
                .save(recipeOutput);

        TitaniumShapelessRecipeBuilder.shapelessRecipe(IFEItems.LASER_LENS_DRAGON.get())
                .requires(Ingredient.of(Arrays.stream(ModuleCore.LASER_LENS).map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList()).stream()))
                .requires(IFEItems.DRAGON_STAR.get(),4)
                .save(recipeOutput);

        TitaniumShapelessRecipeBuilder.shapelessRecipe(ModuleCore.LASER_LENS[0].get())
                .requires(IFEItems.LASER_LENS_SCULK.get())
                .requires(Tags.Items.DYES_WHITE)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(modId,"laser_lens0_sculk"));

        TitaniumShapelessRecipeBuilder.shapelessRecipe(ModuleCore.LASER_LENS[0].get())
                .requires(IFEItems.LASER_LENS_DRAGON.get())
                .requires(Tags.Items.DYES_WHITE)
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(modId,"laser_lens0_dragon"));

        TitaniumShapedRecipeBuilder.shapedRecipe(Items.SCULK)
                .pattern("AA")
                .pattern("AA")
                .define('A',Items.ECHO_SHARD)
                .save(recipeOutput);

        TitaniumShapedRecipeBuilder.shapedRecipe(IFEItems.SIMULATED_CARD.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("AEA")
                .define('A', IndustrialTags.Items.PLASTIC)
                .define('B', Items.NETHER_STAR)
                .define('C', Items.OBSERVER)
                .define('D', Items.REPEATER)
                .define('E', IndustrialTags.Items.GEAR_GOLD)
                .save(recipeOutput);


        IFESerializableProvider.init(recipeOutput);
    }
}
