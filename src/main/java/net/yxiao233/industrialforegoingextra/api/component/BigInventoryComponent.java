package net.yxiao233.industrialforegoingextra.api.component;

import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.DataComponentUtil;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BigInventoryComponent<T extends IComponentHarness> extends InventoryComponent<T> {
    private static final Codec<ItemStack> CODEC = Codec.lazyInitialized(() -> {
        return RecordCodecBuilder.create((p_347288_) -> {
            return p_347288_.group(ItemStack.ITEM_NON_AIR_CODEC.fieldOf("id").forGetter(ItemStack::getItemHolder), ExtraCodecs.intRange(1, Integer.MAX_VALUE).fieldOf("count").orElse(1).forGetter(ItemStack::getCount), DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter((getter) -> {
                return ((PatchedDataComponentMap) getter.getComponents()).asPatch();
            })).apply(p_347288_, ItemStack::new);
        });
    });

    public BigInventoryComponent(String name, int xPos, int yPos, int size) {
        super(name, xPos, yPos, size);
    }

    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.validateSlotIndex(slot);
            ItemStack existingStack = this.stacks.get(slot);
            int limit = this.getSlotLimit(slot);
            if (!existingStack.isEmpty()) {
                if (!ItemStack.isSameItemSameComponents(stack, existingStack)) {
                    return stack;
                }

                limit -= existingStack.getCount();
            }

            if (limit <= 0) {
                return stack;
            } else {
                boolean reachedLimit = stack.getCount() > limit;
                if (!simulate) {
                    if (existingStack.isEmpty()) {
                        this.stacks.set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
                    } else {
                        existingStack.grow(reachedLimit ? limit : stack.getCount());
                    }

                    this.onContentsChanged(slot);
                }

                return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
            }
        }
    }

    public @NotNull CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        ListTag nbtTagList = new ListTag();

        for(int i = 0; i < this.stacks.size(); ++i) {
            if (!this.stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                nbtTagList.add(save(this.stacks.get(i),provider,itemTag));
            }
        }

        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", this.stacks.size());
        return nbt;
    }

    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        this.setSize(nbt.contains("Size", 3) ? nbt.getInt("Size") : this.stacks.size());
        ListTag tagList = nbt.getList("Items", 10);

        for(int i = 0; i < tagList.size(); ++i) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < this.stacks.size()) {
                parse(provider, itemTags).ifPresent(stack -> {
                    this.stacks.set(slot,stack);
                });
            }
        }

        this.onLoad();
    }

    private Tag save(ItemStack stack, HolderLookup.Provider levelRegistryAccess, Tag outputTag) {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Cannot encode empty ItemStack");
        } else {
            return DataComponentUtil.wrapEncodingExceptions(stack, CODEC, levelRegistryAccess, outputTag);
        }
    }

    private static Optional<ItemStack> parse(HolderLookup.Provider lookupProvider, Tag tag) {
        return CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag).resultOrPartial(error -> {
            IndustrialForegoingExtra.LOGGER.error("Tried to load invalid item: '{}'", error);
        });
    }
}
