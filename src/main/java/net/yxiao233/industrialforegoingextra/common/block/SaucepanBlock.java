package net.yxiao233.industrialforegoingextra.common.block;

import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.common.Tags;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;
import net.yxiao233.industrialforegoingextra.common.tile.SaucepanTile;
import org.jetbrains.annotations.NotNull;

public class SaucepanBlock extends IFEBlock<SaucepanTile> {
    public SaucepanBlock() {
        super("saucepan", Properties.ofFullCopy(Blocks.IRON_BLOCK), SaucepanTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return SaucepanTile::new;
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void registerRecipe(@NotNull RecipeOutput output) {
        TitaniumShapedRecipeBuilder.shapedRecipe(this)
                .pattern("PDP")
                .pattern("SMS")
                .pattern("ARA")
                .define('P', IndustrialTags.Items.PLASTIC)
                .define('D', IFETags.Items.GEARS_NETHERITE)
                .define('S', Tags.Items.SLIMEBALLS)
                .define('A', Items.BRICK)
                .define('M', IndustrialTags.Items.MACHINE_FRAME_PITY)
                .define('R', ItemTags.MEAT)
                .save(output);
    }
}
