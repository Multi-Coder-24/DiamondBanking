//package org.multicoder.diamondbanking.command;
//import com.mojang.brigadier.CommandDispatcher;
///*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
///*     */ import com.mojang.brigadier.arguments.StringArgumentType;
///*     */ import com.mojang.brigadier.context.CommandContext;
///*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
///*     */ import java.time.LocalDateTime;
///*     */ import net.minecraft.ChatFormatting;
///*     */ import net.minecraft.commands.CommandBuildContext;
///*     */ import net.minecraft.commands.CommandSourceStack;
///*     */ import net.minecraft.commands.Commands;
///*     */ import net.minecraft.commands.arguments.EntityArgument;
///*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
///*     */ import net.minecraft.commands.arguments.item.ItemArgument;
///*     */ import net.minecraft.core.BlockPos;
///*     */ import net.minecraft.network.chat.Component;
///*     */ import net.minecraft.network.chat.MutableComponent;
///*     */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
///*     */ import net.minecraft.server.level.ServerLevel;
///*     */ import net.minecraft.server.level.ServerPlayer;
///*     */ import net.minecraft.world.item.DyeColor;
///*     */ import net.minecraft.world.item.Item;
///*     */ import net.minecraft.world.item.ItemStack;
///*     */ import net.minecraft.world.level.ItemLike;
///*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
///*     */ import net.minecraft.world.level.block.entity.SignText;
///*     */ import net.neoforged.neoforge.common.Tags;
///*     */ import net.neoforged.neoforge.network.PacketDistributor;
///*     */ import org.multicoder.diamondbanking.Diamondbanking;
///*     */ import org.multicoder.diamondbanking.data.DBDataAttachments;
///*     */ import org.multicoder.diamondbanking.data.DBTransaction;
///*     */ import org.multicoder.diamondbanking.data.SignShopData;
///*     */ import org.multicoder.diamondbanking.network.DBTransactionSyncS2CPacket;
///*     */
///*     */ public class EconomyCommands {
///*  38 */   public static final MutableComponent ECONOMY_PREFIX = Component.translatable("text.diamondbanking.economy").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD);
///*     */
///*     */   public static void registerEconomyCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
///*  41 */     dispatcher.register(Commands.literal("Diamond").then(Commands.literal("Economy").then(Commands.literal("Pay").then(Commands.argument("player", EntityArgument.player()).then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes(EconomyCommands::PayPlayer)))))).createBuilder().build();
///*  42 */     dispatcher.register(Commands.literal("Diamond").then(Commands.literal("Economy").then(Commands.literal("Shop").then(Commands.argument("sign_position", BlockPosArgument.blockPos()).then(Commands.argument("Price", IntegerArgumentType.integer(1)).then(Commands.argument("chest_location", BlockPosArgument.blockPos()).then(Commands.argument("item", ItemArgument.item(context)).then(Commands.argument("Amount", IntegerArgumentType.integer(1)).then(Commands.argument("Name", StringArgumentType.string()).executes(EconomyCommands::ShopInit)))))))))).createBuilder().build();
///*  43 */     dispatcher.register(Commands.literal("Diamond").then(Commands.literal("Economy").then(Commands.literal("Shop").then(Commands.argument("sign_position", BlockPosArgument.blockPos()).then(Commands.argument("Price", IntegerArgumentType.integer(1)).then(Commands.argument("chest_location", BlockPosArgument.blockPos()).then(Commands.argument("Name", StringArgumentType.string()).executes(EconomyCommands::ShopInitHeldItem)))))))).createBuilder().build();
///*  44 */     dispatcher.register(Commands.literal("Diamond").then(Commands.literal("Economy").then(Commands.literal("Process").executes(EconomyCommands::Process)))).createBuilder().build();
///*     */   }
///*     */
///*     */   private static int ShopInitHeldItem(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///*  48 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///*  49 */     ServerLevel world = ((CommandSourceStack)context.getSource()).getLevel();
///*  50 */     BlockPos signPosition = BlockPosArgument.getBlockPos(context, "sign_position");
///*  51 */     BlockPos chestPosition = BlockPosArgument.getBlockPos(context, "chest_location");
///*  52 */     int price = IntegerArgumentType.getInteger(context, "Price");
///*  53 */     String Name = StringArgumentType.getString(context, "Name");
///*  54 */     ItemStack saleStack = new ItemStack((ItemLike)player.getMainHandItem().getItem(), player.getMainHandItem().getCount());
///*  55 */     if (world.getBlockState(signPosition).is(Diamondbanking.SIGNS) && world.getBlockState(chestPosition).is(Tags.Blocks.CHESTS)) {
///*  56 */       SignBlockEntity signBlockEntity = (SignBlockEntity)world.getBlockEntity(signPosition);
///*  57 */       if (signBlockEntity != null) {
///*  58 */         if (signBlockEntity.hasData(DBDataAttachments.SIGN_SHOP)) {
///*  59 */           SignShopData data = (SignShopData)signBlockEntity.getData(DBDataAttachments.SIGN_SHOP);
///*  60 */           if (data.MerchantID.equals(player.getGameProfile().id())) {
///*  61 */             Component[] Data = { (Component)Component.literal(Name), (Component)saleStack.getDisplayName().copy().append(" X " + String.valueOf(saleStack.getCount())), (Component)Component.literal(String.valueOf(price) + " Diamonds"), player.getName() };
///*  62 */             signBlockEntity.setText(new SignText(Data, Data, DyeColor.WHITE, true), true);
///*  63 */             signBlockEntity.setWaxed(true);
///*  64 */             signBlockEntity.setData(DBDataAttachments.SIGN_SHOP, new SignShopData(saleStack, price, player.getGameProfile().id(), chestPosition, Name));
///*  65 */             signBlockEntity.syncData(DBDataAttachments.SIGN_SHOP);
///*  66 */             player.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_reset").withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///*     */           } else {
///*     */
///*  69 */             player.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_in_use").withStyle(new ChatFormatting[] { ChatFormatting.DARK_RED, ChatFormatting.BOLD })));
///*     */           }
///*     */         } else {
///*     */
///*  73 */           Component[] Data = { (Component)Component.literal(Name), (Component)saleStack.getDisplayName().copy().append(" X " + String.valueOf(saleStack.getCount())), (Component)Component.literal(String.valueOf(price) + " Diamonds"), player.getName() };
///*  74 */           signBlockEntity.setText(new SignText(Data, Data, DyeColor.WHITE, true), true);
///*  75 */           signBlockEntity.setWaxed(true);
///*  76 */           signBlockEntity.setData(DBDataAttachments.SIGN_SHOP, new SignShopData(saleStack, price, player.getGameProfile().id(), chestPosition, Name));
///*  77 */           signBlockEntity.syncData(DBDataAttachments.SIGN_SHOP);
///*  78 */           player.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_set").withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///*     */         }
///*     */       }
///*     */     }
///*     */
///*  83 */     return 0;
///*     */   }
///*     */
///*     */   private static int Process(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///*  87 */     ServerPlayer serverPlayer = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///*  88 */     ShopTransactions.processAllTransactions(serverPlayer.getGameProfile().id(), serverPlayer);
///*  89 */     int Balance = ((Integer)serverPlayer.getData(DBDataAttachments.PLAYER_ACCOUNT)).intValue();
///*  90 */     serverPlayer.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_process", new Object[] { Integer.valueOf(Balance) }).withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///*  91 */     return 0;
///*     */   }
///*     */
///*     */   private static int ShopInit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///*  95 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///*  96 */     ServerLevel world = ((CommandSourceStack)context.getSource()).getLevel();
///*  97 */     BlockPos signPosition = BlockPosArgument.getBlockPos(context, "sign_position");
///*  98 */     BlockPos chestPosition = BlockPosArgument.getBlockPos(context, "chest_location");
///*  99 */     Item saleItem = (Item)ItemArgument.getItem(context, "item").item().value();
///* 100 */     int price = IntegerArgumentType.getInteger(context, "Price");
///* 101 */     int amount = IntegerArgumentType.getInteger(context, "Amount");
///* 102 */     String Name = StringArgumentType.getString(context, "Name");
///* 103 */     ItemStack saleStack = new ItemStack((ItemLike)saleItem, amount);
///* 104 */     if (world.getBlockState(signPosition).is(Diamondbanking.SIGNS) && world.getBlockState(chestPosition).is(Tags.Blocks.CHESTS)) {
///* 105 */       SignBlockEntity signBlockEntity = (SignBlockEntity)world.getBlockEntity(signPosition);
///* 106 */       if (signBlockEntity != null) {
///* 107 */         if (signBlockEntity.hasData(DBDataAttachments.SIGN_SHOP)) {
///* 108 */           SignShopData data = (SignShopData)signBlockEntity.getData(DBDataAttachments.SIGN_SHOP);
///* 109 */           if (data.MerchantID.equals(player.getGameProfile().id())) {
///* 110 */             Component[] Data = { (Component)Component.literal(Name), (Component)saleStack.getDisplayName().copy().append(" X " + String.valueOf(saleStack.getCount())), (Component)Component.literal(String.valueOf(price) + " Diamonds"), player.getName() };
///* 111 */             signBlockEntity.setWaxed(true);
///* 112 */             signBlockEntity.setText(new SignText(Data, Data, DyeColor.WHITE, true), true);
///* 113 */             signBlockEntity.setData(DBDataAttachments.SIGN_SHOP, new SignShopData(saleStack, price, player.getGameProfile().id(), chestPosition, Name));
///* 114 */             signBlockEntity.syncData(DBDataAttachments.SIGN_SHOP);
///* 115 */             player.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_reset").withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///*     */           }
///*     */           else {
///*     */
///* 119 */             player.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_in_use").withStyle(new ChatFormatting[] { ChatFormatting.DARK_RED, ChatFormatting.BOLD })));
///*     */           }
///*     */         } else {
///*     */
///* 123 */           Component[] Data = { (Component)Component.literal(Name), (Component)saleStack.getDisplayName().copy().append(" X " + String.valueOf(amount)), (Component)Component.literal(String.valueOf(price) + " Diamonds"), player.getName() };
///* 124 */           signBlockEntity.setText(new SignText(Data, Data, DyeColor.WHITE, true), true);
///* 125 */           signBlockEntity.setWaxed(true);
///* 126 */           signBlockEntity.setData(DBDataAttachments.SIGN_SHOP, new SignShopData(saleStack, price, player.getGameProfile().id(), chestPosition, Name));
///* 127 */           signBlockEntity.syncData(DBDataAttachments.SIGN_SHOP);
///* 128 */           player.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.shop_set").withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///*     */         }
///*     */       }
///*     */     }
///* 132 */     return 0;
///*     */   }
///*     */
///*     */   private static int PayPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///* 136 */     ServerPlayer payor = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///* 137 */     ServerPlayer payee = EntityArgument.getPlayer(context, "player");
///* 138 */     if (payee != payor) {
///* 139 */       int amount = IntegerArgumentType.getInteger(context, "amount");
///* 140 */       int BalancePayor = ((Integer)payor.getData(DBDataAttachments.PLAYER_ACCOUNT)).intValue();
///* 141 */       int BalancePayee = ((Integer)payee.getData(DBDataAttachments.PLAYER_ACCOUNT)).intValue();
///* 142 */       if (BalancePayor < amount) {
///* 143 */         payee.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.payment_recieved_failed", new Object[] { payor.getName(), Integer.valueOf(amount) }).withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD)));
///* 144 */         payor.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.payment_sent_failed", new Object[] { payor.getName(), Integer.valueOf(amount), Integer.valueOf(BalancePayor) }).withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD)));
///*     */       } else {
///*     */
///* 147 */         DBTransaction transaction = new DBTransaction(payee.getGameProfile().id(), payor.getGameProfile().id(), amount, LocalDateTime.now());
///* 148 */         transaction.saveToServer(((CommandSourceStack)context.getSource()).getServer());
///* 149 */         BalancePayor -= amount;
///* 150 */         BalancePayee += amount;
///* 151 */         payee.setData(DBDataAttachments.PLAYER_ACCOUNT, Integer.valueOf(BalancePayee));
///* 152 */         payor.setData(DBDataAttachments.PLAYER_ACCOUNT, Integer.valueOf(BalancePayor));
///* 153 */         payee.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.payment_recieved_success", new Object[] { Integer.valueOf(amount), payor.getName(), Integer.valueOf(BalancePayee) }).withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///* 154 */         payee.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("command.response.diamondbanking.payment_sent_sucess", new Object[] { Integer.valueOf(amount), payor.getName(), Integer.valueOf(BalancePayor) }).withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.BOLD })));
///* 155 */         payee.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("text.response.diamondbanking.payment_transaction", new Object[] { transaction.transactionID().toString() }).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE)));
///* 156 */         payor.sendSystemMessage((Component)ECONOMY_PREFIX.copy().append((Component)Component.translatable("text.response.diamondbanking.payment_transaction", new Object[] { transaction.transactionID().toString() }).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE)));
///* 157 */         PacketDistributor.sendToPlayer(payee, (CustomPacketPayload)new DBTransactionSyncS2CPacket(transaction.transactionID(), transaction.payeeID(), transaction.payorID(), transaction.Amount(), transaction.timeStamp()), new CustomPacketPayload[0]);
///* 158 */         PacketDistributor.sendToPlayer(payor, (CustomPacketPayload)new DBTransactionSyncS2CPacket(transaction.transactionID(), transaction.payeeID(), transaction.payorID(), transaction.Amount(), transaction.timeStamp()), new CustomPacketPayload[0]);
///*     */       }
///*     */     }
///* 161 */     return 0;
///*     */   }
///*     */ }
//
//
///* Location:              C:\Users\multicoder\Downloads\diamondbanking-3.0.0.jar!\org\multicoder\diamondbanking\commands\EconomyCommands.class
// * Java compiler version: 25 (69.0)
// * JD-Core Version:       1.1.3
// */