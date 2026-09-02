package org.multicoder.diamond_banking;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.multicoder.diamond_banking.core.DiamondBankingBlockEntities;
import org.multicoder.diamond_banking.core.DiamondBankingBlocks;
import org.multicoder.diamond_banking.core.DiamondBankingDataAttachments;
import org.multicoder.diamond_banking.core.DiamondBankingItems;
import org.slf4j.Logger;

@Mod(DiamondBanking.MODID)
public class DiamondBanking {
    public static final String MODID = "diamond_banking";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DiamondBanking(IEventBus modEventBus, ModContainer ignored) {
        LOGGER.info(DiamondBanking.MODID + " Initializing");
        LOGGER.info(DiamondBanking.MODID + " Initializing Items");
        DiamondBankingItems.ITEMS.register(modEventBus);
        LOGGER.info(DiamondBanking.MODID + " Initializing Blocks");
        DiamondBankingBlocks.BLOCKS.register(modEventBus);
        LOGGER.info(DiamondBanking.MODID + " Initializing Block Entities");
        DiamondBankingBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        LOGGER.info(DiamondBanking.MODID + " Initializing Data Attachments");
        DiamondBankingDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        LOGGER.info(DiamondBanking.MODID + " Initialized");
    }
}
