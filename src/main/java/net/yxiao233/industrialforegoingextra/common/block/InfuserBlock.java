package net.yxiao233.industrialforegoingextra.common.block;

import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.common.tile.InfuserTile;
import org.jetbrains.annotations.NotNull;

public class InfuserBlock extends IFEBlock<InfuserTile> {
    public InfuserBlock() {
        super("infuser", Properties.ofFullCopy(Blocks.IRON_BLOCK), InfuserTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return InfuserTile::new;
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public void registerRecipe(@NotNull RecipeOutput consumer) {
        TitaniumShapedRecipeBuilder.shapedRecipe(this)
                .pattern("ABA").pattern("BDB").pattern("ABA")
                .define('A', Items.BUCKET)
                .define('B', IndustrialTags.Items.MACHINE_FRAME_PITY)
                .define('D',Items.DRAGON_EGG)
                .save(consumer);
    }
}
