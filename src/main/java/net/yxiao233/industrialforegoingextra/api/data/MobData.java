package net.yxiao233.industrialforegoingextra.api.data;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.common.registry.IFEDataComponents;
import net.yxiao233.industrialforegoingextra.util.CodecHelper;

import java.util.HashMap;
import java.util.List;

public class MobData {
    public static final Codec<AtomicDouble> ATOMIC_DOUBLE_CODEC = Codec.of(
            new Encoder<>() {
                @Override
                public <T> DataResult<T> encode(AtomicDouble input, DynamicOps<T> ops, T prefix) {
                    return Codec.DOUBLE.encode(input.get(), ops, prefix);
                }
            },
            new Decoder<>() {
                @Override
                public <T> DataResult<Pair<AtomicDouble, T>> decode(DynamicOps<T> ops, T input) {
                    return Codec.DOUBLE.decode(ops, input).map(pair -> pair.mapFirst(AtomicDouble::new));
                }
            }
    );
    public static final Codec<MobData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.unboundedMap(Codec.STRING, ATOMIC_DOUBLE_CODEC)
                    .xmap(
                            HashMap::new,
                            hashMap -> hashMap
                    )
                    .fieldOf("data")
                    .forGetter(MobData::getEntities)
    ).apply(builder,MobData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf,MobData> STREAM_CODEC = CodecHelper.fromCodec(CODEC);
    private HashMap<String, AtomicDouble> entities = new HashMap<>();

    public MobData(String entity, double efficiency){
        entities.put(entity,new AtomicDouble(efficiency));
    }

    public MobData(HashMap<String, AtomicDouble> map){
        this.entities = map;
    }

    public HashMap<String, AtomicDouble> getEntities(){
        return entities;
    }

    public static MobData create(LivingEntity entity){
        return new MobData(EntityType.getKey(entity.getType()).toString(), 1);
    }

    public static MobData of(HashMap<String, AtomicDouble> map){
        return new MobData(map);
    }

    public void update(ItemStack card, String entity, double delta){
        entities.get(entity).getAndAdd(delta);
        card.set(IFEDataComponents.MOB_DATA,of(entities));
    }

    public void update(ItemStack card, LivingEntity entity, double delta){
        update(card,EntityType.getKey(entity.getType()).toString(),delta);
    }

    public void put(ItemStack card, String entity, double efficiency){
        entities.put(entity,new AtomicDouble(efficiency));
        card.set(IFEDataComponents.MOB_DATA,of(entities));
    }

    public void put(ItemStack card, LivingEntity entity, double efficiency){
        put(card,EntityType.getKey(entity.getType()).toString(),efficiency);
    }

    public boolean contains(LivingEntity entity){
        return contains(EntityType.getKey(entity.getType()).toString());
    }

    public boolean contains(String entity){
        return this.entities.containsKey(entity);
    }

    public int getEntitySize(){
        return this.entities.size();
    }

    public void appendHoverTip(List<Component> tooltips){
        tooltips.add(Component.translatable("data_type.industrialforegoingextra.mob").withStyle(ChatFormatting.GOLD));
        this.entities.forEach((entity, efficiency) -> tooltips.add(Component.translatable(Util.makeDescriptionId("entity", ResourceLocation.parse(entity))).append(Component.literal(": " + (int) efficiency.get()))));
    }

    public boolean equals(Object other) {
        if(other instanceof MobData data){
            return data == this;
        }
        return false;
    }

    public int hashCode() {
        return this.entities.hashCode();
    }
}
