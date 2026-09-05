///*    */ package org.multicoder.diamondbanking.command;
///*    */ import com.google.gson.Gson;
///*    */ import com.google.gson.JsonObject;
///*    */ import com.google.gson.stream.JsonReader;
///*    */ import com.mojang.brigadier.CommandDispatcher;
///*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
///*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
///*    */ import com.mojang.brigadier.context.CommandContext;
///*    */ import java.io.File;
///*    */ import java.io.FileReader;
///*    */ import java.time.LocalDateTime;
///*    */ import java.time.format.DateTimeFormatter;
///*    */ import java.util.UUID;
///*    */ import net.minecraft.ChatFormatting;
///*    */ import net.minecraft.commands.CommandSourceStack;
///*    */ import net.minecraft.commands.Commands;
///*    */ import net.minecraft.commands.arguments.UuidArgument;
///*    */ import net.minecraft.network.chat.Component;
///*    */ import net.minecraft.server.MinecraftServer;
///*    */ import net.minecraft.server.level.ServerPlayer;
///*    */ import net.minecraft.server.permissions.Permission;
///*    */ import net.minecraft.server.permissions.PermissionLevel;
///*    */ import net.neoforged.fml.loading.FMLPaths;
///*    */ import org.multicoder.diamondbanking.Diamondbanking;
///*    */ import org.multicoder.diamondbanking.data.DBTransaction;
///*    */
///*    */ public class ServerAdminCommands {
///*    */   public static void registerAdminCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
///* 29 */     dispatcher.register((LiteralArgumentBuilder)Commands.literal("DBEcon").then(Commands.literal("Transaction").then(((RequiredArgumentBuilder)Commands.argument("transactionID", (ArgumentType)UuidArgument.uuid()).requires(source -> source.permissions().hasPermission((Permission)new Permission.HasCommandLevel(PermissionLevel.ADMINS)))).executes(ServerAdminCommands::FetchTransaction)))).createBuilder().build();
///*    */   }
///*    */
///*    */   private static int FetchTransaction(CommandContext<CommandSourceStack> context) {
///*    */     try {
///* 34 */       UUID transactionID = UuidArgument.getUuid(context, "transactionID");
///* 35 */       MinecraftServer minecraftserver = ((CommandSourceStack)context.getSource()).getServer();
///* 36 */       ServerPlayer admin = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///* 37 */       File Transaction = minecraftserver.getFile(String.valueOf(FMLPaths.GAMEDIR.get().toFile()) + "/Diamond Banking Server/TRN-" + String.valueOf(FMLPaths.GAMEDIR.get().toFile()) + ".json").toFile();
///* 38 */       if (Transaction.exists()) {
///* 39 */         JsonReader reader = new JsonReader(new FileReader(Transaction));
///* 40 */         Gson gson = new Gson();
///* 41 */         JsonObject object = (JsonObject)gson.fromJson(reader, JsonObject.class);
///* 42 */         DBTransaction transaction = new DBTransaction(object);
///* 43 */         UUID payee = transaction.payeeID();
///* 44 */         UUID payor = transaction.payorID();
///* 45 */         int Amount = transaction.Amount();
///* 46 */         String stamp = transaction.timeStamp();
///* 47 */         admin.sendSystemMessage((Component)Component.translatable("command.response.diamondbanking.admin_transaction_query", new Object[] { transactionID.toString(), payee.toString(), payor.toString(), Integer.valueOf(Amount), LocalDateTime.parse(stamp, DateTimeFormatter.ISO_DATE_TIME).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) }).withStyle(ChatFormatting.BOLD));
///*    */       }
///*    */
///* 50 */       return 0;
///*    */     }
///* 52 */     catch (Exception e) {
///* 53 */       Diamondbanking.LOGGER.error("Error while fetching transaction from Diamond Banking Server!", e);
///* 54 */       return -1;
///*    */     }
///*    */   }
///*    */ }
//
//
///* Location:              C:\Users\multicoder\Downloads\diamondbanking-3.0.0.jar!\org\multicoder\diamondbanking\commands\ServerAdminCommands.class
// * Java compiler version: 25 (69.0)
// * JD-Core Version:       1.1.3
// */