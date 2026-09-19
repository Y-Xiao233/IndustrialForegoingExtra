package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.resourceproduction.tile.OreLaserBaseTile;
import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.LaserDrillOreRecipe;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.buuz135.industrial.utils.ItemStackUtils;
import com.buuz135.industrial.utils.ItemStackWeightedItem;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.yxiao233.industrialforegoingextra.api.block.ISimulatedMachine;
import net.yxiao233.industrialforegoingextra.api.data.ISimulatedCard;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(OreLaserBaseTile.class)
public abstract class MixinOreLaserBaseTile extends IndustrialMachineTile<OreLaserBaseTile> implements ILaserBase<OreLaserBaseTile>, ISimulatedMachine<OreLaserBaseTile> {
    @Shadow private SidedInventoryComponent<OreLaserBaseTile> output;

    @Shadow protected abstract ItemStackWeightedItem processRecipe(LaserDrillOreRecipe recipe, VoxelShape box);

    @Shadow private int miningDepth;

    @Shadow public abstract @NotNull OreLaserBaseTile getSelf();

    @Unique
    @Save
    private SidedInventoryComponent<OreLaserBaseTile> ifs$simulated;

    public MixinOreLaserBaseTile(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, blockPos, blockState);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onTileInit(BlockPos blockPos, BlockState blockState, CallbackInfo ci){
        addInventory(this.ifs$simulated = (SidedInventoryComponent<OreLaserBaseTile>) simulatedInventory(9,5,  2).create().setComponentHarness(this.getSelf()));
    }

    /**
     * @author Y_Xiao233
     * @reason mixin
     */
    @Overwrite
    @SuppressWarnings("unchecked")
    private void onWork() {
        if (!ItemStackUtils.isInventoryFull(this.output)) {
            VoxelShape box = Shapes.box(-1.0, 0.0, -1.0, 2.0, 3.0, 2.0).move((double)this.worldPosition.getX(), (double)(this.worldPosition.getY() - 1), (double)this.worldPosition.getZ());
            List<ItemStackWeightedItem> items = RecipeUtil.getRecipes(this.level, (RecipeType<LaserDrillOreRecipe>) ModuleCore.LASER_DRILL_TYPE.get()).stream().filter((laserDrillOreRecipe) -> {
                return LaserDrillRarity.getValidRarity(this.level, laserDrillOreRecipe.rarity, this.level.dimensionType(), this.level.getBiome(this.worldPosition), this.miningDepth) != null;
            }).map((recipe) -> {
                return this.processRecipe(recipe, box);
            }).filter(Objects::nonNull).toList();
            if (!items.isEmpty()) {
                ItemStack stack = WeightedRandom.getRandomItem(this.level.getRandom(), items).get().getStack();

                ItemStack card = this.ifs$simulated.getStackInSlot(0);
                if(!card.isEmpty() && card.getItem() instanceof ISimulatedCard simulatedCard){
                    if (simulatedCard.isDataEmpty(card)) {
                        simulatedCard.createOreData(level,card,stack);
                    }else if(simulatedCard.is(card,"ore")){
                        simulatedCard.updateOreData(level,card,stack,1);
                    }
                }

                ItemHandlerHelper.insertItem(this.output, stack, false);
            }
        }
    }

    public SidedInventoryComponent<OreLaserBaseTile> industrialForegoingExtra$getSimulatedInventory() {
        return ifs$simulated;
    }
}
