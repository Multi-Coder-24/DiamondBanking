package org.multicoder.diamond_banking;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(DiamondBanking.MODID)
public class DiamondBanking {
    public static final String MODID = "diamond_banking";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DiamondBanking(IEventBus modEventBus, ModContainer modContainer) {

    }
}
