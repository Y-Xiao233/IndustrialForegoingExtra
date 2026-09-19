package net.yxiao233.industrialforegoingextra.api.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;

@SuppressWarnings("unused")
public record DeferredRecipe<T extends Recipe<?>>(ResourceLocation rl, DeferredHolder<RecipeSerializer<?>, RecipeSerializer<T>> serializer, DeferredHolder<RecipeType<?>, RecipeType<?>> type) {
    public RecipeSerializer<T> asSerializer() {
        return serializer.get();
    }

    public RecipeSerializer<?> asUnknownSerializer() {
        return serializer.get();
    }

    public RecipeType<?> asUnknownType() {
        return type.get();
    }

    @SuppressWarnings("unchecked")
    public RecipeType<T> asType() {
        return (RecipeType<T>) type.get();
    }
}
