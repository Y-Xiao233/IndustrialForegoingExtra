package net.yxiao233.industrialforegoingextra.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.data.FluidData;
import net.yxiao233.industrialforegoingextra.api.data.MobData;
import net.yxiao233.industrialforegoingextra.api.data.OreData;
import net.yxiao233.industrialforegoingextra.util.CodecHelper;

public class IFEDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, IndustrialForegoingExtra.MODID);
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<MobData>> MOB_DATA = DATA_COMPONENTS.register("mob_data",() -> DataComponentType.<MobData>builder().persistent(MobData.CODEC).networkSynchronized(MobData.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<OreData>> ORE_DATA = DATA_COMPONENTS.register("ore_data",() -> DataComponentType.<OreData>builder().persistent(OreData.CODEC).networkSynchronized(OreData.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<FluidData>> FLUID_DATA = DATA_COMPONENTS.register("fluid_data",() -> DataComponentType.<FluidData>builder().persistent(FluidData.CODEC).networkSynchronized(FluidData.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<String>> DATA_TYPE = DATA_COMPONENTS.register("data_type",() -> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(CodecHelper.fromCodec(Codec.STRING)).build());
}
