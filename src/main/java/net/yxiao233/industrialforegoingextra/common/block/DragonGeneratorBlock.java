package net.yxiao233.industrialforegoingextra.common.block;

import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;
import net.yxiao233.industrialforegoingextra.common.tile.DragonGeneratorTile;
import org.jetbrains.annotations.NotNull;

public class DragonGeneratorBlock extends IFEBlock<DragonGeneratorTile> {
    public DragonGeneratorBlock() {
        super("dragon_generator", Properties.ofFullCopy(Blocks.IRON_BLOCK), DragonGeneratorTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return DragonGeneratorTile::new;
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }


    @Override
    public void registerRecipe(@NotNull RecipeOutput consumer) {
        TitaniumShapedRecipeBuilder.shapedRecipe(this)
                .pattern("ACA").pattern("DFE").pattern("GBG")
                .define('A', IndustrialTags.Items.PLASTIC)
                .define('B', Blocks.FURNACE)
                .define('C', IFEItems.EFFICIENCY_ADDONS.getAddonItem(6).get())
                .define('D', IFEItems.PROCESSING_ADDONS.getAddonItem(6).get())
                .define('E', IFEItems.SPEED_ADDONS.getAddonItem(6).get())
                .define('F', IndustrialTags.Items.MACHINE_FRAME_SUPREME)
                .define('G', IFETags.Items.GEARS_NETHERITE)
                .save(consumer);
    }
}
