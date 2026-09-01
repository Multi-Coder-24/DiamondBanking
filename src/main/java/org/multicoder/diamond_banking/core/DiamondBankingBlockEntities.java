package org.multicoder.diamond_banking.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.multicoder.diamond_banking.DiamondBanking;
import org.multicoder.diamond_banking.block.entity.PostBoxBlockEntity;

public class DiamondBankingBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DiamondBanking.MODID);

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<?>> POST_BOX_ENTITY = BLOCK_ENTITIES.register("post_box_entity",() -> new BlockEntityType<>(PostBoxBlockEntity::new,DiamondBankingBlocks.POST_BOX.get()));
}
