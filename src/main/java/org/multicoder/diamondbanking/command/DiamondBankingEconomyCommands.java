package org.multicoder.diamondbanking.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;

public class DiamondBankingEconomyCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(Commands.literal(Component.translatable("command.diamondbanking.prefix").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.economy").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.pay").getString()).then(Commands.argument(Component.translatable("command.diamondbanking.player").getString(), EntityArgument.player()).then(Commands.argument(Component.translatable("command.diamondbanking.amount").getString(), IntegerArgumentType.integer(1)).executes(DiamondBankingEconomyCommands::payPlayer)))))).createBuilder().build();
        dispatcher.register(Commands.literal(Component.translatable("command.diamondbanking.prefix").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.economy").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.shop").getString()).then(Commands.argument(Component.translatable("command.diamondbanking.sign_position").getString(), BlockPosArgument.blockPos()).then(Commands.argument(Component.translatable("command.diamondbanking.price").getString(),IntegerArgumentType.integer(1)).then(Commands.argument(Component.translatable("command.diamondbanking.chest_position").getString(),BlockPosArgument.blockPos()).then(Commands.argument(Component.translatable("command.diamondbanking.item").getString(), ItemArgument.item(context)).then(Commands.argument(Component.translatable("command.diamondbanking.amount").getString(),IntegerArgumentType.integer(1)).then(Commands.argument(Component.translatable("command.diamondbanking.name").getString(), StringArgumentType.string()).executes(DiamondBankingEconomyCommands::initShop)))))))))).createBuilder().build();
        dispatcher.register(Commands.literal(Component.translatable("command.diamondbanking.prefix").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.economy").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.shop").getString()).then(Commands.argument(Component.translatable("command.diamondbanking.sign_position").getString(), BlockPosArgument.blockPos()).then(Commands.argument(Component.translatable("command.diamondbanking.price").getString(),IntegerArgumentType.integer(1)).then(Commands.argument(Component.translatable("command.diamondbanking.chest_position").getString(),BlockPosArgument.blockPos()).then(Commands.argument(Component.translatable("command.diamondbanking.name").getString(), StringArgumentType.string()).executes(DiamondBankingEconomyCommands::initShopHeldItem)))))))).createBuilder().build();
        dispatcher.register(Commands.literal(Component.translatable("command.diamondbanking.prefix").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.economy").getString()).then(Commands.literal(Component.translatable("command.diamondbanking.process").getString()).executes(DiamondBankingEconomyCommands::process)))).createBuilder().build();
    }

    private static int initShopHeldItem(CommandContext<CommandSourceStack> context) {
        return 0;
    }

    private static int initShop(CommandContext<CommandSourceStack> context) {
        return 0;
    }

    private static int process(CommandContext<CommandSourceStack> context) {
        return 0;
    }

    private static int payPlayer(CommandContext<CommandSourceStack> context) {
        return 0;
    }
}
