package net.yxiao233.industrialforegoingextra.common.registry;

import com.buuz135.industrial.module.IModule;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.api.block.BoundingBlock;
import net.yxiao233.industrialforegoingextra.api.tile.BoundingTile;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryHelper;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryModule;
import net.yxiao233.industrialforegoingextra.common.block.*;
import net.yxiao233.industrialforegoingextra.util.RarityHelper;

@RegistryModule
public class IFEBlocks implements IModule {
    public static DeferredHolder<Block, Block> DEAD_DRAGON_EGG;
    public static DeferredHolder<Block, Block> ULTIMATE_MACHINE_FRAME;
    public static DeferredHolder<Block, Block> DRAGON_STAR_BLOCK;
    public static BlockWithTile ARCANE_DRAGON_EGG_FORGING;
    public static BlockWithTile INFUSER;
    public static BlockWithTile CREATIVE_CAPACITOR;
    public static BlockWithTile SAUCEPAN;
    public static BlockWithTile FERMENTER;
    public static BlockWithTile BOUNDING;
    public static BlockWithTile BIG_DISSOLUTION_CHAMBER;
    public static BlockWithTile DRAGON_GENERATOR;
    public static BlockWithTile FLUID_CRAFTING_TABLE;
    public static BlockWithTile SIMULATED_MOB_DUPLICATOR;
    public static BlockWithTile SIMULATED_MOB_CRUSHER;
    public static BlockWithTile SIMULATED_ORE_LASER_BASE;
    public static BlockWithTile SIMULATED_FLUID_LASER_BASE;
    @Override
    @SuppressWarnings("ConstantConditions")
    public void generateFeatures(DeferredRegistryHelper helper) {
        DEAD_DRAGON_EGG = RegistryHelper.blockWithItem(helper,"dead_dragon_egg",blockProperties -> new DeadDragonEggBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRAGON_EGG)), RarityHelper::epic);
        ULTIMATE_MACHINE_FRAME = RegistryHelper.simpleBlockWithItem(helper,"ultimate_machine_frame", RarityHelper::supreme);
        DRAGON_STAR_BLOCK = RegistryHelper.simpleBlockWithItem(helper,"dragon_star_block", RarityHelper::supreme);
        ARCANE_DRAGON_EGG_FORGING = RegistryHelper.blockWithTileItem(helper,"arcane_dragon_egg_forging", ArcaneDragonEggForgingBlock::new);
        INFUSER = RegistryHelper.blockWithTileItem(helper,"infuser", InfuserBlock::new);
        CREATIVE_CAPACITOR = RegistryHelper.blockWithTileItem(helper,"creative_capacitor", CreativeCapacitorBlock::new);
        SAUCEPAN = RegistryHelper.blockWithTileItem(helper,"saucepan", SaucepanBlock::new);
        FERMENTER = RegistryHelper.blockWithTileItem(helper,"fermenter", FermenterBlock::new);
        BOUNDING = RegistryHelper.blockWithTile(helper,"bounding",
                () -> new BoundingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),
                (block) -> () -> BlockEntityType.Builder.of(BoundingTile::new, block.get()).build(null)
        );
        BIG_DISSOLUTION_CHAMBER = RegistryHelper.bigBlockWithTileItem(helper,"big_dissolution_chamber", BigDissolutionChamberBlock::new);
        DRAGON_GENERATOR = RegistryHelper.blockWithTileItem(helper,"dragon_generator",DragonGeneratorBlock::new);
        FLUID_CRAFTING_TABLE = RegistryHelper.blockWithTileItem(helper,"fluid_crafting_table",FluidCraftingTableBlock::new);
        SIMULATED_MOB_DUPLICATOR = RegistryHelper.blockWithTileItem(helper,"simulated_mob_duplicator",SimulatedMobDuplicatorBlock::new);
        SIMULATED_MOB_CRUSHER = RegistryHelper.blockWithTileItem(helper,"simulated_mob_crusher",SimulatedMobCrusherBlock::new);
        SIMULATED_ORE_LASER_BASE = RegistryHelper.blockWithTileItem(helper,"simulated_ore_laser_base",SimulatedOreLaserBaseBlock::new);
        SIMULATED_FLUID_LASER_BASE = RegistryHelper.blockWithTileItem(helper,"simulated_fluid_laser_base",SimulatedFluidLaserBaseBlock::new);
    }
}
