package net.yxiao233.industrialforegoingextra.util;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class IntegrationHelper {
    public static boolean isModLoadedBeforeRegistry(String modId){
        return !LoadingModList.get().getMods().stream().filter(info -> info.getModId().equals(modId)).toList().isEmpty();
    }
    public static boolean isModLoaded(String modId){
        return ModList.get().isLoaded(modId);
    }
}
