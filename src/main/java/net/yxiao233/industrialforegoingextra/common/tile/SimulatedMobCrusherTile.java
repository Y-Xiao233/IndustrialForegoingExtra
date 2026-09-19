package net.yxiao233.industrialforegoingextra.common.tile;

import com.buuz135.industrial.IndustrialForegoing;
import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.StateButtonAddon;
import com.hrznstudio.titanium.client.screen.addon.StateButtonInfo;
import com.hrznstudio.titanium.component.button.ButtonComponent;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.api.block.ISimulatedMachine;
import net.yxiao233.industrialforegoingextra.api.component.BigSidedInventoryComponent;
import net.yxiao233.industrialforegoingextra.api.data.MobData;
import net.yxiao233.industrialforegoingextra.api.data.SimulatedCard;
import net.yxiao233.industrialforegoingextra.common.config.machine.SimulatedMobCrusherConfig;
import net.yxiao233.industrialforegoingextra.common.item.IFELootingAddonItem;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;
import net.yxiao233.industrialforegoingextra.util.SimulatedMachineEfficiencyHelper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatedMobCrusherTile extends IndustrialWorkingTile<SimulatedMobCrusherTile> implements ISimulatedMachine<SimulatedMobCrusherTile> {
    private final int maxProgress;
    private final int powerPerOperation;
    private final Method DROP_CUSTOM_DEATH_LOOT = ObfuscationReflectionHelper.findMethod(Mob.class, "dropCustomDeathLoot", new Class[]{ServerLevel.class, DamageSource.class, Boolean.TYPE});;
    @Save
    private BigSidedInventoryComponent<SimulatedMobCrusherTile> output;
    @Save
    private SidedFluidTankComponent<SimulatedMobCrusherTile> tank;
    @Save
    private SidedInventoryComponent<SimulatedMobCrusherTile> simulated;
    @Save
    private boolean dropXP;
    private final WorkAction ignore = new WorkAction(1.0f,0);
    @SuppressWarnings({"deprecation","unchecked"})
    public SimulatedMobCrusherTile(BlockPos blockPos, BlockState blockState) {
        super(IFEBlocks.SIMULATED_MOB_CRUSHER, SimulatedMobCrusherConfig.powerPerOperation, blockPos, blockState);
        if (!this.DROP_CUSTOM_DEATH_LOOT.isAccessible()) {
            this.DROP_CUSTOM_DEATH_LOOT.setAccessible(true);
        }

        this.dropXP = true;
        this.addTank(this.tank = (SidedFluidTankComponent<SimulatedMobCrusherTile>)(new SidedFluidTankComponent<SimulatedMobCrusherTile>("essence", SimulatedMobCrusherConfig.tankSize, 43, 20, 0)).setColor(DyeColor.LIME)
                .setTankAction(FluidTankComponent.Action.DRAIN)
                .setComponentHarness(this)
                .setValidator((fluidStack) -> fluidStack.getFluid().is(IndustrialTags.Fluids.EXPERIENCE))
        );
        this.addInventory(this.output = (BigSidedInventoryComponent<SimulatedMobCrusherTile>)(new BigSidedInventoryComponent<SimulatedMobCrusherTile>("output", 64, 22, 18, 1)).setColor(DyeColor.ORANGE).setRange(6, 3)
                .setInputFilter((stack, integer) -> false)
                .setSlotLimit(SimulatedMobCrusherConfig.maxOutputSlotLimit)
                .setComponentHarness(this)
        );
        this.addButton(new ButtonComponent(154 - 18, 84, 14, 14) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public @NotNull List<IFactory<? extends IScreenAddon>> getScreenAddons() {
                return Collections.singletonList(() -> new StateButtonAddon(this,
                        new StateButtonInfo(0, AssetTypes.BUTTON_SIDENESS_ENABLED, ChatFormatting.GOLD + LangUtil.getString("tooltip.industrialforegoing.mob_crusher.produce"), "tooltip.industrialforegoing.mob_crusher.produce_extra"),
                        new StateButtonInfo(1, AssetTypes.BUTTON_SIDENESS_DISABLED, ChatFormatting.GOLD + LangUtil.getString("tooltip.industrialforegoing.mob_crusher.consume"), "tooltip.industrialforegoing.mob_crusher.consume_extra_1", "tooltip.industrialforegoing.mob_crusher.consume_extra_2")) {
                    @Override
                    public int getState() {
                        return dropXP ? 0 : 1;
                    }
                });
            }
        }.setPredicate((playerEntity, compoundNBT) -> {
            this.dropXP = !this.dropXP;
            markForUpdate();
        }));

        this.addInventory(this.simulated = (SidedInventoryComponent<SimulatedMobCrusherTile>) simulatedInventory(88,80,2).create()
                .setComponentHarness(this)
        );
        this.maxProgress = SimulatedMobCrusherConfig.maxProgress;
        this.powerPerOperation = SimulatedMobCrusherConfig.powerPerOperation;
    }

    @Override
    public IndustrialWorkingTile<SimulatedMobCrusherTile>.WorkAction work() {
        if(this.level == null || this.getEnergyStorage().getEnergyStored() < this.powerPerOperation){
            return ignore;
        }

        ItemStack card = this.simulated.getStackInSlot(0);
        if(card.isEmpty()){
            return ignore;
        }

        if(!card.has(IFEDataComponents.MOB_DATA)){
            return ignore;
        }

        MobData data = card.get(IFEDataComponents.MOB_DATA);
        if(data == null){
            return ignore;
        }

        FakePlayer player = IndustrialForegoing.getFakePlayer(this.level, this.getBlockPos(), this.getUuid());
        AtomicBoolean worked = new AtomicBoolean(false);
        data.getEntities().forEach((entityType, efficiency) ->{
            if(!entityType.isEmpty()){
                Optional<EntityType<?>> optional = EntityType.byString(entityType);
                optional.ifPresent(type -> {
                    Entity entity = type.create(this.level);
                    if(entity instanceof LivingEntity livingEntity){
                        applyOutput(livingEntity,player,efficiency.get());
                        ((SimulatedCard) card.getItem()).updateMobData(card,livingEntity,1);
                        worked.set(true);
                    }
                });
            }
        });

        return worked.get() ? new WorkAction(1.0f,this.powerPerOperation) : ignore;
    }

    @SuppressWarnings("ConstantConditions")
    private void applyOutput(LivingEntity entity, FakePlayer player, double efficiency){
        int experience = entity.getExperienceReward((ServerLevel)this.level, player);
        int tier = AugmentInventoryHelper.getAugmentTier(this, IFEAddonType.LOOTING) + 1;
        int multiple = SimulatedMachineEfficiencyHelper.getMultiple(efficiency,tier);

        if(entity.getType().is(Tags.EntityTypes.BOSSES)){
            int chance = level.random.nextInt(10);
            multiple = chance <= 1 ? multiple / 2 : 0;
        }

        if (!this.dropXP) {
            int looting = this.level.random.nextInt(4);
            ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            ItemEnchantments.Mutable enchants = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            EnchantmentHelper.setEnchantments(sword, enchants.toImmutable());
            player.setItemInHand(InteractionHand.MAIN_HAND, sword);
            enchants.set(this.level.registryAccess().holderOrThrow(Enchantments.LOOTING), looting);
        }

        DamageSource source = player.damageSources().playerAttack(player);
        LootTable table = this.level.getServer().reloadableRegistries().getLootTable(entity.getLootTable());
        LootParams.Builder context = (new LootParams.Builder((ServerLevel)this.level)).withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.DAMAGE_SOURCE, source).withParameter(LootContextParams.ORIGIN, new Vec3((double)this.worldPosition.getX(), (double)this.worldPosition.getY(), (double)this.worldPosition.getZ())).withParameter(LootContextParams.ATTACKING_ENTITY, player).withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player).withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, player);
        int finalMultiple = multiple;
        table.getRandomItems(context.create(LootContextParamSets.ENTITY)).forEach((stack) -> {
            int count = stack.copy().getCount();
            ItemHandlerHelper.insertItem(this.output, stack.copyWithCount(finalMultiple * count), false);
        });

        List<ItemEntity> extra = new ArrayList<>();
        try {
            if (entity.captureDrops() == null) entity.captureDrops(new ArrayList<>());
            DROP_CUSTOM_DEATH_LOOT.invoke(entity, (ServerLevel) this.level, source, true);
            if (entity.captureDrops() != null) {
                extra.addAll(entity.captureDrops());
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {}
        CommonHooks.onLivingDrops(entity, source, extra, true);
        extra.forEach(itemEntity -> {
            int count = itemEntity.getItem().copy().getCount();
            ItemHandlerHelper.insertItem(this.output, itemEntity.getItem().copyWithCount(finalMultiple * count), false);
            itemEntity.remove(Entity.RemovalReason.KILLED);
        });
        if (dropXP)
            this.tank.fillForced(new FluidStack(ModuleCore.ESSENCE.getSourceFluid().get(), experience * 20), IFluidHandler.FluidAction.EXECUTE);
        entity.setHealth(0);
        entity.remove(Entity.RemovalReason.KILLED);
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    }


    @Override
    public boolean canAcceptAugment(ItemStack augment) {
        if(augment.getItem() instanceof IFELootingAddonItem){
            return AugmentInventoryHelper.canAccept(this,augment);
        }
        return super.canAcceptAugment(augment);
    }

    @NotNull
    @Override
    public SimulatedMobCrusherTile getSelf() {
        return this;
    }

    @Override
    public SidedInventoryComponent<SimulatedMobCrusherTile> industrialForegoingExtra$getSimulatedInventory() {
        return simulated;
    }

    @Override
    protected @NotNull EnergyStorageComponent<SimulatedMobCrusherTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(SimulatedMobCrusherConfig.maxStoredPower, 10, 20);
    }

    @Override
    public int getMaxProgress() {
        return this.maxProgress;
    }

    @Override
    public void saveSettings(Player player, CompoundTag tag) {
        tag.putBoolean("SMC_drops", dropXP);
        super.saveSettings(player, tag);
    }

    @Override
    public void loadSettings(Player player, CompoundTag tag) {
        if (tag.contains("SMC_drops")) {
            this.dropXP = tag.getBoolean("SMC_drops");
        }
        super.loadSettings(player, tag);
    }
}
