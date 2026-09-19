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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import net.yxiao233.industrialforegoingextra.util.CodecHelper;

import java.util.ArrayList;
import java.util.List;

public class FluidData {
    public static final Codec<FluidData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            CompoundTag.CODEC.listOf().fieldOf("data").forGetter(FluidData::getTags)
    ).apply(builder,FluidData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidData> STREAM_CODEC = CodecHelper.fromCodec(CODEC);
    private List<CompoundTag> tags = new ArrayList<>();
    public FluidData(Level level, FluidStack fluidStack, double efficiency){
        CompoundTag tag = new CompoundTag();
        tag.putString("id",toString(fluidStack));
        tag.put("fluid",fluidStack.saveOptional(level.registryAccess()));
        tag.putDouble("efficiency",efficiency);
        tags.add(tag);
    }

    private FluidData(List<CompoundTag> tags){
        this.tags = tags;
    }

    public List<CompoundTag> getTags(){
        return tags;
    }

    public static FluidData create(Level level, FluidStack fluidStack){
        return new FluidData(level, fluidStack, 1);
    }

    public static FluidData of(List<CompoundTag> tags){
        return new FluidData(tags);
    }

    public void update(ItemStack card, FluidStack fluidStack, double delta){
        tags.forEach(tag ->{
            if(tag.contains("id")){
                String id = tag.getString("id");
                if(id.equals(toString(fluidStack))){
                    double efficiency = tag.getDouble("efficiency");
                    tag.putDouble("efficiency",efficiency + delta);
                }
            }
        });
        card.set(IFEDataComponents.FLUID_DATA,of(tags));
    }

    public void put(Level level, ItemStack card, FluidStack fluidStack, double efficiency){
        FluidData fluidData = card.get(IFEDataComponents.FLUID_DATA);
        List<CompoundTag> newList = new ArrayList<>();
        if(fluidData != null){
            newList.addAll(this.tags);
        }
        CompoundTag tag = new CompoundTag();
        tag.putString("id",toString(fluidStack));
        tag.put("fluid",fluidStack.saveOptional(level.registryAccess()));
        tag.putDouble("efficiency",efficiency);
        newList.add(tag);
        card.set(IFEDataComponents.FLUID_DATA,of(newList));
    }
    public boolean contains(FluidStack fluidStack){
        boolean contains = false;
        for (CompoundTag tag : tags) {
            if (tag.contains("id")) {
                contains = tag.getString("id").equals(toString(fluidStack));
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }

    public int getFluidSize(){
        return this.tags.size();
    }

    public void appendHoverTip(HolderLookup.Provider provider, List<Component> tooltips){
        tooltips.add(Component.translatable("data_type.industrialforegoingextra.fluid").withStyle(ChatFormatting.GOLD));
        this.tags.forEach(tag -> {
            FluidStack fluidStack = FluidStack.parseOptional(provider,tag.getCompound("fluid"));
            double efficiency = tag.getDouble("efficiency");
            tooltips.add(Component.translatable(fluidStack.getDescriptionId()).append(Component.literal(": " + (int) efficiency)));
        });
    }

    private String toString(FluidStack fluid){
        return BuiltInRegistries.FLUID.getKey(fluid.getFluid()).toString();
    }

    public boolean equals(Object other) {
        if(other instanceof FluidData data){
            return data == this;
        }
        return false;
    }

    public int hashCode() {
        return this.tags.hashCode();
    }
}
