package net.yxiao233.industrialforegoingextra.mixin;

import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class IFEMixinPlugin implements IMixinConfigPlugin {
    private boolean industrialForegoingSouls = false;
    @Override
    public void onLoad(String mixinPackage) {
        for(ModInfo info : LoadingModList.get().getMods()){
            if(info.getModId().equals("industrialforegoingsouls")){
                industrialForegoingSouls = true;
            }
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        List<String> list = List.of(
                "net.yxiao233.ifs.mixin.tile.MixinSoulLaserBaseBlockEntity"
        );

        if(list.contains(mixinClassName)){
            return industrialForegoingSouls;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
