package net.yxiao233.industrialforegoingextra.api.registry;

import com.buuz135.industrial.block.IndustrialBlockItem;
import com.buuz135.industrial.module.IModule;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.fluid.ClientFluidTypeExtensions;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import com.hrznstudio.titanium.recipe.serializer.CodecRecipeSerializer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.addon.AddonMap;
import net.yxiao233.industrialforegoingextra.api.addon.IAddonType;
import net.yxiao233.industrialforegoingextra.api.block.IBoundingBlockProvider;
import net.yxiao233.industrialforegoingextra.api.block.MultiBlock;
import net.yxiao233.industrialforegoingextra.util.BigBlockCapabilitiesHelper;
import net.yxiao233.industrialforegoingextra.api.item.BigIndustrialBlockItem;
import net.yxiao233.industrialforegoingextra.api.fluid.fluid.AnimateFluidInstance;
import net.yxiao233.industrialforegoingextra.api.fluid.fluid.BaseFluidInstance;
import net.yxiao233.industrialforegoingextra.util.AnnotationUtil;
import org.apache.commons.lang3.IntegerRange;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryHelper {
    public static AddonMap registryAddons(DeferredRegistryHelper helper, IAddonType type, int maxTier, AddonSupplier supplier){
        AddonMap map = new AddonMap(type);
        if(maxTier < 0){
            map.put(type.getTypeId() + "_addon",helper.registerGeneric(Registries.ITEM,type.getTypeId() + "_addon",() -> supplier.get(maxTier)));
            return map;
        }
        for (int i = 1; i <= maxTier; i++) {
            int finalI = i;
            map.put(type.getTypeId() + "_addon_" + i,helper.registerGeneric(Registries.ITEM,type.getTypeId() + "_addon_" + i,() -> supplier.get(finalI)));
        }
        return map;
    }

    public static AddonMap registryAddons(DeferredRegistryHelper helper, IAddonType type, IntegerRange range, AddonSupplier supplier){
        AddonMap map = new AddonMap(type);
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            int finalI = i;
            map.put(type.getTypeId() + "_addon_" + i,helper.registerGeneric(Registries.ITEM,type.getTypeId() + "_addon_" + i,() -> supplier.get(finalI)));
        }
        return map;
    }

    public static DeferredHolder<Item, Item> simpleItem(DeferredRegistryHelper helper, String name){
        return simpleItem(helper,name,true);
    }

    public static DeferredHolder<Item, Item> simpleItem(DeferredRegistryHelper helper, String name, boolean addToTab){
        return helper.registerGeneric(Registries.ITEM,name,() -> {
            Item item = new Item(new Item.Properties());
            if(addToTab){
                IndustrialForegoingExtra.TAB.getTabList().add(item);
            }
            return item;
        });
    }

    public static DeferredHolder<Item, Item> simpleItem(DeferredRegistryHelper helper, String name, PropertiesCallBack callBack){
        return helper.registerGeneric(Registries.ITEM,name,() -> {
            Item item = new Item(callBack.apply(new Item.Properties()));
            IndustrialForegoingExtra.TAB.getTabList().add(item);
            return item;
        });
    }

    public static DeferredHolder<Item, Item> item(DeferredRegistryHelper helper, String name, Supplier<Item> supplier, boolean addToTab){
        return helper.registerGeneric(Registries.ITEM,name,() -> {
            Item item = supplier.get();
            if(addToTab){
                IndustrialForegoingExtra.TAB.getTabList().add(item);
            }
            return item;
        });
    }

    public static DeferredHolder<Item, Item> item(DeferredRegistryHelper helper, String name, Supplier<Item> supplier){
        return item(helper,name,supplier,true);
    }

    public static DeferredHolder<Block, Block> simpleBlockWithItem(DeferredRegistryHelper helper, String name){
        return helper.registerBlockWithItem(name,() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),(block) -> () -> new BlockItem(block.get(),new Item.Properties()), IndustrialForegoingExtra.TAB);
    }

    public static DeferredHolder<Block, Block> simpleBlockWithItem(DeferredRegistryHelper helper, String name, PropertiesCallBack callBack){
        return helper.registerBlockWithItem(name,() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),(block) -> () -> new BlockItem(block.get(),callBack.apply(new Item.Properties())), IndustrialForegoingExtra.TAB);
    }

    public static DeferredHolder<Block, Block> blockWithItem(DeferredRegistryHelper helper, String name, BlockCallBack blockCallBack, Function<DeferredHolder<Block, Block>, Supplier<Item>> itemFunction){
        return helper.registerBlockWithItem(name,() -> blockCallBack.apply(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),itemFunction,IndustrialForegoingExtra.TAB);
    }

    public static DeferredHolder<Block, Block> blockWithItem(DeferredRegistryHelper helper, String name, Supplier<Block> blockSupplier, Function<DeferredHolder<Block, Block>, Supplier<Item>> itemFunction){
        return helper.registerBlockWithItem(name,blockSupplier,itemFunction,IndustrialForegoingExtra.TAB);
    }

    public static DeferredHolder<Block, Block> blockWithItem(DeferredRegistryHelper helper, String name, Supplier<Block> blockSupplier, PropertiesCallBack callBack){
        return helper.registerBlockWithItem(name,blockSupplier,(block) -> () -> new BlockItem(block.get(),callBack.apply(new Item.Properties())),IndustrialForegoingExtra.TAB);
    }

    public static DeferredHolder<Block, Block> blockWithItem(DeferredRegistryHelper helper, String name, BlockCallBack blockCallBack, PropertiesCallBack callBack){
        return helper.registerBlockWithItem(name,() -> blockCallBack.apply(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),(block) -> () -> new BlockItem(block.get(),callBack.apply(new Item.Properties())),IndustrialForegoingExtra.TAB);
    }

    public static DeferredHolder<Block, Block> blockWithItem(DeferredRegistryHelper helper, String name, BlockCallBack blockCallBack){
        return helper.registerBlockWithItem(name,() -> blockCallBack.apply(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),(block) -> () -> new BlockItem(block.get(),new Item.Properties()),IndustrialForegoingExtra.TAB);
    }

    public static BlockWithTile blockWithTileItem(DeferredRegistryHelper helper, String name, Supplier<BasicTileBlock<?>> blockSupplier){
        return helper.registerBlockWithTileItem(name,blockSupplier, (blockRegistryObject) -> () -> new IndustrialBlockItem(blockRegistryObject.get(), IndustrialForegoingExtra.TAB),IndustrialForegoingExtra.TAB);
    }

    public static BlockWithTile blockWithTile(DeferredRegistryHelper helper, String name, Supplier<Block> blockSupplier, Function<Supplier<Block>,Supplier<BlockEntityType<?>>> entitySupplier){
        DeferredHolder<Block, Block> block = helper.registerGeneric(Registries.BLOCK, name, blockSupplier);
        DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> blockEntityType = helper.registerBlockEntityType(name, entitySupplier.apply(block));
        return new BlockWithTile(block,blockEntityType);
    }

    public static BlockWithTile bigBlockWithTileItem(DeferredRegistryHelper helper, String name, Supplier<MultiBlock<?>> blockSupplier){
        IEventBus eventBus = ModList.get().getModContainerById(IndustrialForegoingExtra.MODID).orElseThrow().getEventBus();
        DeferredHolder<Block, Block> blockRegistryObject = helper.registerBlockWithItem(name, blockSupplier, (block) -> () -> new BigIndustrialBlockItem(block.get(),IndustrialForegoingExtra.TAB), IndustrialForegoingExtra.TAB);
        DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> type = helper.registerBlockEntityType(name, () -> BlockEntityType.Builder.of(((BasicTileBlock<?>)blockRegistryObject.get()).getTileEntityFactory(), new Block[]{blockRegistryObject.get()}).build(null));
        if(eventBus != null){
            eventBus.addListener(RegisterCapabilitiesEvent.class, event -> BigBlockCapabilitiesHelper.registryCapabilities(event,type));
        }
        return new BlockWithTile(blockRegistryObject, type);
    }

    public static BaseFluidInstance simpleFluid(DeferredRegistryHelper helper, String name){
        return new BaseFluidInstance(
                helper,
                name,
                FluidType.Properties.create().density(1000),
                new ClientFluidTypeExtensions(
                        IndustrialForegoingExtra.makeId("block/fluids/" + name + "_still"),
                        IndustrialForegoingExtra.makeId("block/fluids/" + name + "_flow")
                )
        );
    }

    public static <T extends AnimateFluidInstance> T animateFluid(DeferredRegistryHelper helper, String name, FluidCallBack<T> fluidCallBack){
        return fluidCallBack.of(
                helper,
                name,
                FluidType.Properties.create().density(1000),
                new ClientFluidTypeExtensions(
                        IndustrialForegoingExtra.makeId("block/fluids/" + name + "_still"),
                        IndustrialForegoingExtra.makeId("block/fluids/" + name + "_flow")
                )
        );
    }

    public static <T extends Recipe<?>> DeferredRecipe<T> codecRecipe(DeferredRegistryHelper helper, String name, Class<T> clazz, MapCodec<T> codec){
        DeferredHolder<RecipeType<?>, RecipeType<?>> type = helper.registerGeneric(Registries.RECIPE_TYPE,name,() -> RecipeType.simple(IndustrialForegoingExtra.makeId(name)));
        DeferredHolder<RecipeSerializer<?>, RecipeSerializer<T>> serializer = helper.registerTyped(Registries.RECIPE_SERIALIZER,name, () -> new CodecRecipeSerializer<>(clazz,type,codec));
        return new DeferredRecipe<>(IndustrialForegoingExtra.makeId(name),serializer,type);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Recipe<?>> DeferredRecipe<T> codecRecipe(DeferredRegistryHelper helper, String name, Class<T> clazz){
        MapCodec<T> codec = null;
        try{
            for (Field field : clazz.getFields()) {
                if(Modifier.isStatic(field.getModifiers()) && field.getName().equalsIgnoreCase("codec")){
                    Object o = field.get(null);
                    Class<?> mapCodecClass = MapCodec.class;
                    if(mapCodecClass.isInstance(o)){
                        codec = (MapCodec<T>) mapCodecClass.cast(o);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return codecRecipe(helper,name,clazz,codec);
    }

    @FunctionalInterface
    public interface AddonSupplier {
        Item get(int tier);
    }

    @FunctionalInterface
    public interface PropertiesCallBack {
        Item.Properties apply(Item.Properties properties);
    }

    @FunctionalInterface
    public interface BlockCallBack {
        Block apply(BlockBehaviour.Properties properties);
    }

    @FunctionalInterface
    public interface FluidCallBack<T extends AnimateFluidInstance> {
        T of(DeferredRegistryHelper helper, String name, FluidType.Properties properties, ClientFluidTypeExtensions extensions);
    }

    @SuppressWarnings("deprecation")
    public static void initAllRegistryModule(DeferredRegistryHelper registryHelper){
        AnnotationUtil.getAllClasses(RegistryModule.class).forEach(clazz ->{
            if(IModule.class.isAssignableFrom(clazz)){
                try {
                    ((IModule) clazz.newInstance()).generateFeatures(registryHelper);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
