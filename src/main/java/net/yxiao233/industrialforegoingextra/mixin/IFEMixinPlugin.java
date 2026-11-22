package net.yxiao233.industrialforegoingextra.mixin;

import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class IFEMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE_NAME = "net.yxiao233.industrialforegoingextra.mixin";
    @Override
    public void onLoad(String mixinPackage) {
        MixinLoadedCondition.addCondition("industrialforegoingsouls");
        MixinLoadedCondition.addCondition("productivebees");
        MixinLoadedCondition.init();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return MixinLoadedCondition.shouldApply(mixinClassName);
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

    private static class MixinLoadedCondition {
        private static final HashMap<String, Boolean> modMap = new HashMap<>();
        public static void addCondition(String modid){
            modMap.put(modid, false);
        }
        public static void init(){
            for(ModInfo info : LoadingModList.get().getMods()){
                if(modMap.containsKey(info.getModId())){
                    modMap.merge(info.getModId(),true,(oldValue, newValue) ->{
                        return true;
                    });
                }
            }
        }
        public static boolean shouldApply(String mixinClassName){
            AtomicBoolean shouldApply = new AtomicBoolean(false);
            String[] array = modMap.keySet().toArray(String[]::new);
            for (String modid : array) {
                Boolean shouldLoaded = modMap.get(modid);
                if(mixinClassName.startsWith(MIXIN_PACKAGE_NAME + "." + modid + ".")){
                    shouldApply.set(shouldLoaded);
                    break;
                }
            }
            return shouldApply.get();
        }
    }
}
