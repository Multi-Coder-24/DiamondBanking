package org.multicoder.diamond_banking.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.multicoder.diamond_banking.core.DiamondBankingDataAttachments;
import org.multicoder.diamond_banking.data.PlayerAccount;

import java.util.Objects;

public class ATMBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ATMBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack itemStack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if(!level.isClientSide()){
            PlayerAccount account = player.getData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get());
            if(itemStack.is(Items.DIAMOND)) {
                int Count = itemStack.getCount();
                account.IncreaseBalance(Count);
                int Balance = account.getBalance();
                player.setData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get(),account);
                player.sendSystemMessage(Component.literal("You have Deposited " + Count + " Diamonds, Your Balance is now " + Balance));
                itemStack.shrink(Count);
                return InteractionResult.CONSUME;
            }
            else if(itemStack.is(Blocks.DIAMOND_BLOCK.asItem())) {
                int Count = itemStack.getCount();
                Count = Count * 9;
                account.IncreaseBalance(Count);
                int Balance = account.getBalance();
                player.setData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get(),account);
                player.sendSystemMessage(Component.literal("You have Deposited " + Count + " Diamonds, Your Balance is now " + Balance));
                itemStack.shrink(Count);
                return InteractionResult.CONSUME;
            }
            else if(itemStack.isEmpty()){
                if(!player.isShiftKeyDown()){
                    int Balance = account.getBalance();
                    player.sendSystemMessage(Component.literal("You Have " + Balance + " Diamonds"));
                    return InteractionResult.CONSUME;
                }
                else{
                    if(account.DecreaseBalance(1)){
                        ItemStack stack = new ItemStack(Items.DIAMOND);
                        player.addItem(stack);
                        player.sendSystemMessage(Component.literal("You Have Withdrawn 1 Diamond"));
                        player.setData(DiamondBankingDataAttachments.PLAYER_ACCOUNT.get(), account);
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
