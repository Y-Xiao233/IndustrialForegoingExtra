package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrialforegoingsouls.block.tile.SoulLaserBaseBlockEntity;
import com.buuz135.industrialforegoingsouls.config.ConfigSoulLaserBase;
import com.hrznstudio.titanium.api.augment.AugmentTypes;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.item.AugmentWrapper;
import com.hrznstudio.titanium.module.BlockWithTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.yxiao233.industrialforegoingextra.compact.ars_nouveau.ArsNouveauCompact;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(SoulLaserBaseBlockEntity.class)
public abstract class MixinSoulLaserBaseBlockEntity  extends IndustrialMachineTile<SoulLaserBaseBlockEntity> implements ILaserBase<SoulLaserBaseBlockEntity> {
    @Shadow @Final private SidedInventoryComponent<SoulLaserBaseBlockEntity> catalyst;

    @Shadow private int soulAmount;

    @Shadow @Final private ProgressBarComponent<SoulLaserBaseBlockEntity> work;

    public MixinSoulLaserBaseBlockEntity(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, blockPos, blockState);
    }


    /**
     * @author Y_Xiao233
     * @reason mixin
     */
    @Overwrite
    private void onWork() {
        if(this.level == null){
            return;
        }

        if (!this.catalyst.getStackInSlot(0).isEmpty() && this.catalyst.getStackInSlot(0).getItem().equals(ModuleCore.LASER_LENS[11].get()) && this.soulAmount < ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT) {
            if(ModList.get().isLoaded("ars_nouveau")){
                LivingEntity entityInMobJar = ArsNouveauCompact.getMobJarEntity(this.level,this.worldPosition);
                if(entityInMobJar != null){
                    if(entityInMobJar.getType().equals(EntityType.WARDEN) || entityInMobJar.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "wardens")))){
                        this.soulAmount = Math.min(ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT, this.soulAmount + ConfigSoulLaserBase.SOULS_PER_OPERATION);
                        this.syncObject(this.soulAmount);
                        return;
                    }
                }
            }

            VoxelShape box = Shapes.box(-1.0, 0.0, -1.0, 2.0, 3.0, 2.0).move(this.worldPosition.getX(), this.worldPosition.getY() - 1, this.worldPosition.getZ());
            List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, box.bounds(), (entity) -> {
                return entity.getType().equals(EntityType.WARDEN) || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "wardens")));
            });
            if (!entities.isEmpty()) {
                LivingEntity first = entities.getFirst();
                if (first.getHealth() > (float)ConfigSoulLaserBase.DAMAGE_PER_OPERATION || ConfigSoulLaserBase.KILL_WARDEN) {
                    first.hurt(first.damageSources().generic(), (float)ConfigSoulLaserBase.DAMAGE_PER_OPERATION);
                    this.soulAmount = Math.min(ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT, this.soulAmount + ConfigSoulLaserBase.SOULS_PER_OPERATION);
                    this.syncObject(this.soulAmount);
                }
            }
        }

        int maxProgress = (int)Math.floor((float)ConfigSoulLaserBase.MAX_PROGRESS * (this.hasAugmentInstalled(AugmentTypes.EFFICIENCY) ? AugmentWrapper.getType(this.getInstalledAugments(AugmentTypes.EFFICIENCY).getFirst(), AugmentTypes.EFFICIENCY) : 1.0F));
        this.work.setMaxProgress(maxProgress);
    }


    /**
     * @author Y_Xiao233
     * @reason mixin
     */
    @Overwrite
    public void clientTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull SoulLaserBaseBlockEntity blockEntity) {
        super.clientTick(level, pos, state, blockEntity);
        if(this.level == null){
            return;
        }

        if(ModList.get().isLoaded("ars_nouveau")){
            LivingEntity entityInMobJar = ArsNouveauCompact.getMobJarEntity(this.level,this.worldPosition);
            if(entityInMobJar instanceof Warden warden){
                warden.sonicBoomAnimationState.start(warden.tickCount - 43);
                if (level.random.nextDouble() <= 0.10000000149011612) {
                    level.addParticle(ParticleTypes.SCULK_SOUL, entityInMobJar.getX() + (level.random.nextDouble() - 0.5), entityInMobJar.getY() + 1.5, entityInMobJar.getZ() + (level.random.nextDouble() - 0.5), 0.0, 0.1, 0.0);
                    return;
                }
            }
        }

        VoxelShape box = Shapes.box(-1.0, 0.0, -1.0, 2.0, 3.0, 2.0).move(this.worldPosition.getX(), this.worldPosition.getY() - 1, this.worldPosition.getZ());
        List<Mob> entities = this.level.getEntitiesOfClass(Mob.class, box.bounds());

        for (Mob entity : entities) {
            if (entity instanceof Warden warden) {
                warden.sonicBoomAnimationState.start(warden.tickCount - 43);
                if (level.random.nextDouble() <= 0.10000000149011612) {
                    level.addParticle(ParticleTypes.SCULK_SOUL, entity.getX() + (level.random.nextDouble() - 0.5), entity.getY() + 1.5, entity.getZ() + (level.random.nextDouble() - 0.5), 0.0, 0.1, 0.0);
                }
            }
        }
    }
}
