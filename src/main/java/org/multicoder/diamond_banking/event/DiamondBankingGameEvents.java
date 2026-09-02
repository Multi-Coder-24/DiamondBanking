package org.multicoder.diamond_banking.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.multicoder.diamond_banking.DiamondBanking;
import org.multicoder.diamond_banking.core.DiamondBankingDataAttachments;
import org.multicoder.diamond_banking.data.PlayerAccount;

@EventBusSubscriber(modid = DiamondBanking.MODID)
public class DiamondBankingGameEvents {
    @SubscribeEvent
    private static void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        if(!player.hasData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get())){
            player.setData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get(),new PlayerAccount(player.getGameProfile().id()));
        }
    }
}
