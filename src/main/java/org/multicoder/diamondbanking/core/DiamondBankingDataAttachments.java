package org.multicoder.diamondbanking.core;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.multicoder.diamondbanking.DiamondBanking;
import org.multicoder.diamondbanking.data.PlayerAccount;
import org.multicoder.diamondbanking.data.SignShopData;

import java.util.function.Supplier;

public class DiamondBankingDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DiamondBanking.MODID);

    public static final Supplier<AttachmentType<PlayerAccount>> PLAYER_ACCOUNT = ATTACHMENT_TYPES.register("player_account",() -> AttachmentType.builder(() -> new PlayerAccount()).serialize(MapCodec.assumeMapUnsafe(PlayerAccount.CODEC)).copyOnDeath().build());
    public static final Supplier<AttachmentType<SignShopData>> SHOP_DATA = ATTACHMENT_TYPES.register("sign_shop_data",() -> AttachmentType.builder(SignShopData::new).serialize(MapCodec.assumeMapUnsafe(SignShopData.CODEC)).build());
}
