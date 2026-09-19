package net.yxiao233.industrialforegoingextra.common.registry;

import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;

public class IFETags {
    public static class Blocks{
        public static final TagKey<Block> MACHINE_FRAME_ULTIMATE = TagUtil.getBlockTag(ResourceLocation.parse("industrialforegoingextra:machine_frame/ultimate"));
        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingExtra.MODID,name));
        }
    }
    public static class Items{
        public static final TagKey<Item> GEARS_NETHERITE = TagUtil.getItemTag(ResourceLocation.parse("c:gears/netherite"));
        public static final TagKey<Item> GEARS = TagUtil.getItemTag(ResourceLocation.parse("c:gears"));
        public static final TagKey<Item> GEARS_SCULK = TagUtil.getItemTag(ResourceLocation.parse("c:gears/sculk"));
        public static final TagKey<Item> MACHINE_FRAME_ULTIMATE = TagUtil.getItemTag(ResourceLocation.parse("industrialforegoingextra:machine_frame/ultimate"));
        public static final TagKey<Item> WRENCH = TagUtil.getItemTag(ResourceLocation.parse("c:wrench"));
        public static final TagKey<Item> DIAMOND = TagUtil.getItemTag(ResourceLocation.parse("c:gems/diamond"));
        public static final TagKey<Item> ROTTEN_CROPS = TagUtil.getItemTag(ResourceLocation.parse("c:crops/rotten"));

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingExtra.MODID,name));
        }
    }
}
