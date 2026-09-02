package org.multicoder.diamond_banking.core;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.multicoder.diamond_banking.DiamondBanking;
import org.multicoder.diamond_banking.data.PlayerAccount;

import java.util.function.Supplier;

public class DiamondBankingDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DiamondBanking.MODID);

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<PlayerAccount>> PLAYER_ACCOUNT = ATTACHMENT_TYPES.register("player_account",() -> AttachmentType.builder((Supplier<PlayerAccount>) PlayerAccount::new).serialize(MapCodec.assumeMapUnsafe(PlayerAccount.CODEC)).sync(PlayerAccount.STREAM_CODEC).build());
}
