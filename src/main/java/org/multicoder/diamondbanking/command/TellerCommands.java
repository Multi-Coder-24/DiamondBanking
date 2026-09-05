///*    */ package org.multicoder.diamondbanking.command;
///*    */ import com.mojang.brigadier.CommandDispatcher;
///*    */ import com.mojang.brigadier.arguments.ArgumentType;
///*    */ import com.mojang.brigadier.arguments.BoolArgumentType;
///*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
///*    */ import com.mojang.brigadier.context.CommandContext;
///*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
///*    */ import net.minecraft.ChatFormatting;
///*    */ import net.minecraft.commands.CommandSourceStack;
///*    */ import net.minecraft.commands.Commands;
///*    */ import net.minecraft.network.chat.Component;
///*    */ import net.minecraft.server.level.ServerPlayer;
///*    */ import net.minecraft.world.item.ItemStack;
///*    */ import net.minecraft.world.item.Items;
///*    */ import net.minecraft.world.level.ItemLike;
///*    */ import org.multicoder.diamondbanking.data.DBDataAttachments;
///*    */
///*    */ public class TellerCommands {
///* 19 */   public static final MutableComponent TELLER_PREFIX = Component.translatable("text.diamondbanking.teller").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD);
///*    */
///*    */   public static void registerTellerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
///* 22 */     dispatcher.register((LiteralArgumentBuilder)Commands.literal("Diamond").then(Commands.literal("Bank").then(Commands.literal("Balance").executes(TellerCommands::Balance)))).createBuilder().build();
///* 23 */     dispatcher.register((LiteralArgumentBuilder)Commands.literal("Diamond").then(Commands.literal("Bank").then(Commands.literal("Deposit").executes(TellerCommands::Deposit)))).createBuilder().build();
///* 24 */     dispatcher.register((LiteralArgumentBuilder)Commands.literal("Diamond").then(Commands.literal("Bank").then(Commands.literal("Withdraw").then(Commands.argument("amount", (ArgumentType)IntegerArgumentType.integer(1)).then(Commands.argument("compact", (ArgumentType)BoolArgumentType.bool()).executes(TellerCommands::Withdraw)))))).createBuilder().build();
///*    */   }
///*    */
///*    */   private static int Withdraw(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///* 28 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///* 29 */     int Balance = ((Integer)player.getData(DBDataAttachments.PLAYER_ACCOUNT)).intValue();
///* 30 */     int Amount = IntegerArgumentType.getInteger(context, "amount");
///* 31 */     boolean Compact = BoolArgumentType.getBool(context, "compact");
///* 32 */     if (Balance < Amount) {
///* 33 */       player.sendSystemMessage((Component)TELLER_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.insufficient_funds", new Object[] { Integer.valueOf(Amount), Integer.valueOf(Balance) }).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE)));
///*    */
///*    */     }
///* 36 */     else if (Compact) {
///* 37 */       int BlockCount = Amount / 9;
///* 38 */       int Remainder = Amount % 9;
///* 39 */       player.addItem(new ItemStack((ItemLike)Items.DIAMOND_BLOCK, BlockCount));
///* 40 */       player.addItem(new ItemStack((ItemLike)Items.DIAMOND, Remainder));
///* 41 */       Balance -= Amount;
///* 42 */       player.setData(DBDataAttachments.PLAYER_ACCOUNT, Integer.valueOf(Balance));
///* 43 */       player.sendSystemMessage((Component)TELLER_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.withdraw", new Object[] { Integer.valueOf(Amount), Integer.valueOf(Balance) }).withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD)));
///*    */     } else {
///*    */
///* 46 */       int Stacks = Amount / 64;
///* 47 */       int Remainder = Amount % 64;
///* 48 */       if (Stacks > 0) {
///* 49 */         for (int stack = 0; stack < Stacks; stack++) {
///* 50 */           player.addItem(new ItemStack((ItemLike)Items.DIAMOND, 64));
///*    */         }
///*    */       }
///* 53 */       player.addItem(new ItemStack((ItemLike)Items.DIAMOND, Remainder));
///* 54 */       Balance -= Amount;
///* 55 */       player.setData(DBDataAttachments.PLAYER_ACCOUNT, Integer.valueOf(Balance));
///* 56 */       player.sendSystemMessage((Component)TELLER_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.withdraw", new Object[] { Integer.valueOf(Amount), Integer.valueOf(Balance) }).withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD)));
///*    */     }
///*    */
///* 59 */     return 0;
///*    */   }
///*    */
///*    */   private static int Deposit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///* 63 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///* 64 */     ItemStack stack = player.getMainHandItem();
///* 65 */     int Balance = ((Integer)player.getData(DBDataAttachments.PLAYER_ACCOUNT)).intValue();
///* 66 */     if (stack.is(Items.DIAMOND)) {
///* 67 */       Balance += stack.getCount();
///* 68 */       player.setData(DBDataAttachments.PLAYER_ACCOUNT, Integer.valueOf(Balance));
///* 69 */       player.sendSystemMessage((Component)TELLER_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.deposit", new Object[] { Integer.valueOf(stack.getCount()), Integer.valueOf(Balance) }).withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD)));
///* 70 */       stack.shrink(stack.getCount());
///* 71 */     } else if (stack.is(Items.DIAMOND_BLOCK)) {
///* 72 */       Balance += stack.getCount() * 9;
///* 73 */       player.setData(DBDataAttachments.PLAYER_ACCOUNT, Integer.valueOf(Balance));
///* 74 */       player.sendSystemMessage((Component)TELLER_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.deposit", new Object[] { Integer.valueOf(stack.getCount() * 9), Integer.valueOf(Balance) }).withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD)));
///* 75 */       stack.shrink(stack.getCount());
///*    */     }
///* 77 */     return 0;
///*    */   }
///*    */
///*    */   private static int Balance(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///* 81 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///* 82 */     player.sendSystemMessage((Component)TELLER_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.balance", new Object[] { player.getData(DBDataAttachments.PLAYER_ACCOUNT) }).withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD)));
///* 83 */     return 0;
///*    */   }
///*    */ }
//
//
///* Location:              C:\Users\multicoder\Downloads\diamondbanking-3.0.0.jar!\org\multicoder\diamondbanking\commands\TellerCommands.class
// * Java compiler version: 25 (69.0)
// * JD-Core Version:       1.1.3
// */