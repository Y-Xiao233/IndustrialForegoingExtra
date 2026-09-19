package net.yxiao233.industrialforegoingextra.mixin;

import com.hrznstudio.titanium.item.AugmentWrapper;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AugmentWrapper.class)
public class MixinAugmentWrapper{
    @Inject(method = "isAugment", at = @At(value = "HEAD"), cancellable = true)
    private static void isAugment(ItemStack augment, CallbackInfoReturnable<Boolean> cir){
        if(augment.getItem() instanceof IFEAddonItem){
            cir.setReturnValue(true);
        }
    }
}
