///*    */ package org.multicoder.diamondbanking.command;
///*    */
///*    */ import com.mojang.brigadier.context.CommandContext;
///*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
///*    */ import java.net.URI;
///*    */ import net.minecraft.ChatFormatting;
///*    */ import net.minecraft.commands.CommandSourceStack;
///*    */ import net.minecraft.network.chat.ClickEvent;
///*    */ import net.minecraft.network.chat.Component;
///*    */ import net.minecraft.network.chat.HoverEvent;
///*    */ import net.minecraft.network.chat.Style;
///*    */ import net.minecraft.server.level.ServerPlayer;
///*    */
///*    */ public class HelpCommand
///*    */ {
///*    */   public static int help(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
///* 17 */     String url = "https://github.com/Multi-Coder-24/DiamondBanking/wiki";
///* 18 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
///* 19 */     player.sendSystemMessage((Component)Component.translatable("command.response.diamondbanking.help").setStyle(Style.EMPTY.withClickEvent((ClickEvent)new ClickEvent.OpenUrl(URI.create(url))).withHoverEvent((HoverEvent)new HoverEvent.ShowText((Component)Component.literal("Open: " + url))).withUnderlined(Boolean.valueOf(true))
///* 20 */           .withColor(ChatFormatting.AQUA)));
///*    */
///* 22 */     return 0;
///*    */   }
///*    */ }
//
//
///* Location:              C:\Users\multicoder\Downloads\diamondbanking-3.0.0.jar!\org\multicoder\diamondbanking\commands\HelpCommand.class
// * Java compiler version: 25 (69.0)
// * JD-Core Version:       1.1.3
// */