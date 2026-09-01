package org.multicoder.diamond_banking.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.multicoder.diamond_banking.block.PostBoxBlock;
import org.multicoder.diamond_banking.core.DiamondBankingBlockEntities;
import org.multicoder.diamond_banking.data.PostedItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class PostBoxBlockEntity extends BlockEntity {
    private List<PostedItem> post;
    public PostBoxBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(DiamondBankingBlockEntities.POST_BOX_ENTITY.get(), worldPosition, blockState);
        post = new ArrayList<>();
    }

    public void getLastPostedItem(ServerPlayer player){
        PostedItem item = post.getLast();
        player.sendSystemMessage(Component.literal("Message From: " + item.sender().toString() + ", " +item.Message()));
        player.addItem(item.item().copy());
        ArrayList<PostedItem> items = new ArrayList<>(post);
        items.removeLast();
        post = items;
        setChanged();
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        ValueOutput.TypedOutputList<PostedItem> list = output.list("post",PostedItem.CODEC);
        post.forEach(list::add);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        ValueInput.TypedInputList<PostedItem> list = input.list("post",PostedItem.CODEC).get();
        list.forEach(post::add);
    }


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity o) {
        PostBoxBlockEntity postBoxBlockEntity = (PostBoxBlockEntity) o;
        if(!postBoxBlockEntity.post.isEmpty()){
            if(!blockState.getValue(PostBoxBlock.HAS_POST)) {
                BlockState updated = blockState.setValue(PostBoxBlock.HAS_POST, true);
                level.setBlockAndUpdate(blockPos, updated);
            }
        }
    }
}
