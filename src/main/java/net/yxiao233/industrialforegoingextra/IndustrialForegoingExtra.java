package net.yxiao233.industrialforegoingextra;

import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.TitaniumTab;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.client.event.IFEClientEvent;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import net.yxiao233.industrialforegoingextra.util.BigBlockCapabilitiesHelper;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryHelper;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.datagen.*;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;


@Mod(IndustrialForegoingExtra.MODID)
public class IndustrialForegoingExtra extends ModuleController {
    public static final String MODID = "industrialforegoingextra";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static TitaniumTab TAB = new TitaniumTab(makeId("main"));
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_MODE_TAB;
    public IndustrialForegoingExtra(IEventBus modEventBus, ModContainer modContainer) {
        super(modContainer);
        modEventBus.addListener(BigBlockCapabilitiesHelper::registryBoundingCapabilities);
        IFEDataComponents.DATA_COMPONENTS.register(modEventBus);

        if(FMLEnvironment.dist == Dist.CLIENT){
            modEventBus.addListener(IFEClientEvent::clientSetup);
        }
    }
    public static ResourceLocation makeId(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID,path);
    }

    @Override
    protected void initModules() {
        RegistryHelper.initAllRegistryModule(getRegistries());

        CREATIVE_MODE_TAB = this.addCreativeTab("main",() -> new ItemStack(IFEItems.SPEED_ADDONS.getAddonItem(3)),MODID + ".main",TAB);
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new IFEBlockStateProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeServer(), IFEBlockLootTablesProvider.create(packOutput,lookupProvider));
        generator.addProvider(event.includeServer(), new IFEItemModelProvider(packOutput,existingFileHelper));
        IFEBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new IFEBlockTagProvider(packOutput,lookupProvider,existingFileHelper));
        generator.addProvider(event.includeServer(), new IFEItemTagProvider(packOutput,lookupProvider,blockTagProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new IFERecipeProvider(event.getGenerator().getPackOutput(),event.getLookupProvider()));
    }
}
