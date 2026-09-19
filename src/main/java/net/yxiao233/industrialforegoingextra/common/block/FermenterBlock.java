package net.yxiao233.industrialforegoingextra.common.block;

import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.common.Tags;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;
import net.yxiao233.industrialforegoingextra.common.tile.FermenterTile;
import org.jetbrains.annotations.NotNull;

public class FermenterBlock extends IFEBlock<FermenterTile> {
    public FermenterBlock() {
        super("fermenter", Properties.ofFullCopy(Blocks.IRON_BLOCK), FermenterTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return FermenterTile::new;
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public void registerRecipe(@NotNull RecipeOutput output) {
        TitaniumShapedRecipeBuilder.shapedRecipe(this)
                .pattern("PDP")
                .pattern("SMS")
                .pattern("ARA")
                .define('P', IndustrialTags.Items.PLASTIC)
                .define('D', IFETags.Items.GEARS_SCULK)
                .define('S', IFETags.Items.ROTTEN_CROPS)
                .define('A', ModuleCore.PINK_SLIME_INGOT.get())
                .define('M', IndustrialTags.Items.MACHINE_FRAME_PITY)
                .define('R', Tags.Items.CROPS)
                .save(output);
    }
}
