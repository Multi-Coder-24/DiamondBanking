package org.multicoder.diamond_banking.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.multicoder.diamond_banking.DiamondBanking;
import org.multicoder.diamond_banking.block.PostBoxBlock;

import java.util.function.Function;

@SuppressWarnings("all")
public class DiamondBankingBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DiamondBanking.MODID);

    public static final DeferredBlock<PostBoxBlock> POST_BOX = register("post_box",PostBoxBlock::new);

    private static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> supplier){
        DeferredBlock<T> value = BLOCKS.registerBlock(name, supplier);
        DiamondBankingItems.ITEMS.registerSimpleBlockItem(value);
        return value;
    }
}
