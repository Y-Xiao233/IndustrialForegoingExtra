package net.yxiao233.industrialforegoingextra.common.item;

import com.buuz135.industrial.config.item.core.MobImprisonmentToolConfig;
import com.buuz135.industrial.item.MobImprisonmentToolItem;
import com.buuz135.industrial.utils.IFAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.common.config.tool.AdvancedMobImprisonmentToolConfig;

public class AdvancedMobImprisonmentToolItem extends MobImprisonmentToolItem {
    public AdvancedMobImprisonmentToolItem() {
        super(IndustrialForegoingExtra.TAB);
    }

    @Override
    public boolean capture(ItemStack stack, LivingEntity target, Player player) {
        if (target.getCommandSenderWorld().isClientSide) {
            return false;
        } else if (!(target instanceof Player) && target.isAlive()) {
            if (this.containsEntity(stack)) {
                return false;
            } else if (!this.canPlayerCaptureEntity(target, player)) {
                return false;
            } else if (this.isBlacklisted(target.getType())) {
                return false;
            } else {
                CompoundTag nbt = new CompoundTag();
                nbt.putString("entity", EntityType.getKey(target.getType()).toString());
                target.saveWithoutId(nbt);
                stack.set(IFAttachments.MOB_IMPRISONMENT_TOOL, nbt);
                target.remove(Entity.RemovalReason.DISCARDED);
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean canPlayerCaptureEntity(LivingEntity entity, Player player){
        var ret = false;
        switch (entity) {
            case AbstractHorse horse -> {
                if (!AdvancedMobImprisonmentToolConfig.onlyOwnerCanCapture || ((horse.isTamed()
                        && horse.getOwnerUUID() != null
                        && horse.getOwnerUUID().equals(player.getUUID())) || !horse.isTamed())) {
                    ret = true;
                }
            }
            case TamableAnimal animal -> {
                if (!AdvancedMobImprisonmentToolConfig.onlyOwnerCanCapture || (animal.isTame() && animal.isOwnedBy(player)) || !animal.isTame()) {
                    ret = true;
                }
            }
            default -> ret = true;
        }
        return ret;
    }
}
