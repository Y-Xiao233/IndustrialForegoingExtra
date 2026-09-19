package net.yxiao233.industrialforegoingextra.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import net.yxiao233.industrialforegoingextra.util.CodecHelper;

import java.util.ArrayList;
import java.util.List;

public class OreData {
    public static final Codec<OreData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            CompoundTag.CODEC.listOf().fieldOf("data").forGetter(OreData::getTags)
    ).apply(builder,OreData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, OreData> STREAM_CODEC = CodecHelper.fromCodec(CODEC);
    private List<CompoundTag> tags = new ArrayList<>();
    public OreData(Level level, ItemStack ore, double efficiency){
        CompoundTag tag = new CompoundTag();
        tag.putString("id",toString(ore));
        tag.put("item",ore.saveOptional(level.registryAccess()));
        tag.putDouble("efficiency",efficiency);
        tags.add(tag);
    }

    private OreData(List<CompoundTag> tags){
        this.tags = tags;
    }

    public List<CompoundTag> getTags(){
        return tags;
    }

    public static OreData create(Level level, ItemStack ore){
        return new OreData(level, ore, 1);
    }

    public static OreData of(List<CompoundTag> tags){
        return new OreData(tags);
    }

    public void update(ItemStack card, ItemStack ore, double delta){
        tags.forEach(tag ->{
            if(tag.contains("id")){
                String id = tag.getString("id");
                if(id.equals(toString(ore))){
                    double efficiency = tag.getDouble("efficiency");
                    tag.putDouble("efficiency",efficiency + delta);
                }
            }
        });
        card.set(IFEDataComponents.ORE_DATA,of(tags));
    }

    public void put(Level level, ItemStack card, ItemStack ore, double efficiency){
        OreData oreData = card.get(IFEDataComponents.ORE_DATA);
        List<CompoundTag> newList = new ArrayList<>();
        if(oreData != null){
            newList.addAll(this.tags);
        }
        CompoundTag tag = new CompoundTag();
        tag.putString("id",toString(ore));
        tag.put("item",ore.saveOptional(level.registryAccess()));
        tag.putDouble("efficiency",efficiency);
        newList.add(tag);
        card.set(IFEDataComponents.ORE_DATA,of(newList));
    }
    public boolean contains(ItemStack ore){
        boolean contains = false;
        for (CompoundTag tag : tags) {
            if (tag.contains("id")) {
                contains = tag.getString("id").equals(toString(ore));
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }

    public int getOreSize(){
        return this.tags.size();
    }

    public void appendHoverTip(HolderLookup.Provider provider, List<Component> tooltips){
        tooltips.add(Component.translatable("data_type.industrialforegoingextra.ore").withStyle(ChatFormatting.GOLD));
        this.tags.forEach(tag -> {
            ItemStack stack = ItemStack.parseOptional(provider,tag.getCompound("item"));
            double efficiency = tag.getDouble("efficiency");
            if(stack.getItem() instanceof BlockItem blockItem){
                tooltips.add(Component.translatable(blockItem.getDescriptionId()).append(Component.literal(": " + (int) efficiency)));
            }else{
                tooltips.add(Component.translatable(stack.getDescriptionId()).append(Component.literal(": " + (int) efficiency)));
            }
        });
    }

    private String toString(ItemStack stack){
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    }

    public boolean equals(Object other) {
        if(other instanceof OreData data){
            return data == this;
        }
        return false;
    }

    public int hashCode() {
        return this.tags.hashCode();
    }
}
