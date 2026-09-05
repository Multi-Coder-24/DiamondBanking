package org.multicoder.diamondbanking.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.multicoder.diamondbanking.DiamondBanking;
import org.multicoder.diamondbanking.command.DiamondBankingEconomyCommands;
import org.multicoder.diamondbanking.core.DiamondBankingDataAttachments;
import org.multicoder.diamondbanking.data.PlayerAccount;

@EventBusSubscriber(modid = DiamondBanking.MODID)
public class DiamondBankingGameEvents {
    @SubscribeEvent
    private static void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        if(!player.hasData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get())){
            player.setData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get(),new PlayerAccount(player.getGameProfile().id()));
        }
    }

    @SubscribeEvent
    private static void RegisterCommands(RegisterCommandsEvent event){
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        DiamondBankingEconomyCommands.registerCommands(dispatcher,event.getBuildContext());
    }
}
