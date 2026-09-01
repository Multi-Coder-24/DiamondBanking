package org.multicoder.diamond_banking.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public record PostedItem(ItemStack item, UUID sender,String Message) {
    public static final Codec<PostedItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(ItemStack.CODEC.fieldOf("item").forGetter(PostedItem::item), UUIDUtil.CODEC.fieldOf("sender").forGetter(PostedItem::sender), Codec.STRING.fieldOf("message").forGetter(PostedItem::Message)).apply(instance, PostedItem::new));
}
