package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.resourceproduction.tile.FluidLaserBaseTile;
import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.LaserDrillFluidRecipe;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.yxiao233.industrialforegoingextra.api.block.ISimulatedMachine;
import net.yxiao233.industrialforegoingextra.api.data.ISimulatedCard;
import net.yxiao233.industrialforegoingextra.compact.ars_nouveau.ArsNouveauCompact;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(FluidLaserBaseTile.class)
public abstract class MixinFluidLaserBaseTile  extends IndustrialMachineTile<FluidLaserBaseTile> implements ILaserBase<FluidLaserBaseTile>, ISimulatedMachine<FluidLaserBaseTile> {
    @Shadow private SidedInventoryComponent<FluidLaserBaseTile> catalyst;
    @Shadow private int miningDepth;
    @Shadow private SidedFluidTankComponent<FluidLaserBaseTile> output;
    @Unique
    @Save
    private SidedInventoryComponent<FluidLaserBaseTile> ifs$simulated;

    public MixinFluidLaserBaseTile(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, blockPos, blockState);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onTileInit(BlockPos blockPos, BlockState blockState, CallbackInfo ci){
        addInventory(this.ifs$simulated = (SidedInventoryComponent<FluidLaserBaseTile>) simulatedInventory(9,5,  2).create().setComponentHarness(this.getSelf()));
    }

    /**
     * @author Y_Xiao233
     * @reason mixin
     */
    @Overwrite
    @SuppressWarnings("unchecked")
    private void onWork() {
        if (!this.catalyst.getStackInSlot(0).isEmpty()) {
            VoxelShape box = Shapes.box(-1.0, 0.0, -1.0, 2.0, 3.0, 2.0).move((double)this.worldPosition.getX(), (double)(this.worldPosition.getY() - 1), (double)this.worldPosition.getZ());
            RecipeUtil.getRecipes(this.level, (RecipeType<LaserDrillFluidRecipe>) ModuleCore.LASER_DRILL_FLUID_TYPE.get()).stream().filter((laserDrillFluidRecipe) -> {
                return laserDrillFluidRecipe.catalyst.test(this.catalyst.getStackInSlot(0));
            }).filter((laserDrillFluidRecipe) -> {
                return LaserDrillRarity.getValidRarity(this.level, laserDrillFluidRecipe.rarity, this.level.dimensionType(), this.level.getBiome(this.worldPosition), this.miningDepth) != null;
            }).findFirst().ifPresent((recipe) -> {
                if (recipe.entityData.isPresent()) {
                    if(ModList.get().isLoaded("ars_nouveau")){
                        LivingEntity entityInMobJar = ArsNouveauCompact.getMobJarEntity(this.level,this.worldPosition);
                        if(entityInMobJar != null){
                            CompoundTag data = new CompoundTag();
                            entityInMobJar.saveWithoutId(data);
                            if(recipe.entityData.get().getEntity().test(entityInMobJar) && NbtUtils.compareNbt(recipe.entityData.get().getData(), data, true)){
                                this.output.fillForced(recipe.output.getFluids()[0].copy(), IFluidHandler.FluidAction.EXECUTE);
                                return;
                            }
                        }
                    }

                    List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, box.bounds(), (entity) -> {
                        return recipe.entityData.get().getEntity().test(entity);
                    });


                    if (entities.isEmpty()) {
                        return;
                    }

                    List<Entity> filtered = entities.stream().filter((Entity) -> {
                        if (recipe.entityData.isEmpty()) {
                            return true;
                        } else {
                            CompoundTag data = new CompoundTag();
                            Entity.saveWithoutId(data);
                            return NbtUtils.compareNbt(recipe.entityData.get().getData(), data, true);
                        }
                    }).collect(Collectors.toList());
                    if (filtered.isEmpty()) {
                        return;
                    }

                    LivingEntity first = entities.getFirst();
                    if (first.getHealth() > 5.0F) {
                        first.hurt(first.damageSources().generic(), 5.0F);
                        this.output.fillForced(recipe.output.getFluids()[0].copy(), IFluidHandler.FluidAction.EXECUTE);
                    }
                } else {
                    FluidStack fluidStack = recipe.output.getFluids()[0].copy();
                    this.output.fillForced(fluidStack, IFluidHandler.FluidAction.EXECUTE);

                    ItemStack card = this.ifs$simulated.getStackInSlot(0);
                    if(!card.isEmpty() && card.getItem() instanceof ISimulatedCard simulatedCard){
                        if(simulatedCard.isDataEmpty(card)){
                            simulatedCard.createFluidData(level,card,fluidStack);
                        }else if(simulatedCard.is(card,"fluid")){
                            simulatedCard.updateFluidData(level,card,fluidStack,1);
                        }
                    }
                }
            });
        }
    }

    @Override
    public SidedInventoryComponent<FluidLaserBaseTile> industrialForegoingExtra$getSimulatedInventory() {
        return ifs$simulated;
    }
}
