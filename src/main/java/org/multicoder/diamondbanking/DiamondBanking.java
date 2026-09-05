package org.multicoder.diamondbanking;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.multicoder.diamondbanking.core.DiamondBankingDataAttachments;
import org.slf4j.Logger;

@Mod(DiamondBanking.MODID)
public class DiamondBanking {
    public static final String MODID = "diamondbanking";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final TagKey<Block> SIGNS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("minecraft", "signs"));
    public static final MutableComponent ECONOMY_PREFIX = Component.translatable("text.diamondbanking.economy").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD);
    public static final MutableComponent TELLER_PREFIX = Component.translatable("text.diamondbanking.teller").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD);

    public DiamondBanking(IEventBus modEventBus, ModContainer ignored) {
        LOGGER.info(DiamondBanking.MODID + " Initializing");
        LOGGER.info(DiamondBanking.MODID + " Initializing Data Attachments");
        DiamondBankingDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        LOGGER.info(DiamondBanking.MODID + " Initialized");
    }
}
