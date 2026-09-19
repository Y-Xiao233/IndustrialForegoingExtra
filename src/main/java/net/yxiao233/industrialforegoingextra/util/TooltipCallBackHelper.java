package net.yxiao233.industrialforegoingextra.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public record TooltipCallBackHelper(Component component) {
    public TooltipCallBackHelper(String translatableKey, ChatFormatting style) {
        this(Component.translatable(translatableKey).withStyle(style));
    }

    public TooltipCallBackHelper(String translatableKey) {
        this(Component.translatable(translatableKey));
    }

}
